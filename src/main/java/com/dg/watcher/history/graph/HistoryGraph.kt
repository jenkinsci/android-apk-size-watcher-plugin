package com.dg.watcher.history.graph

import com.dg.watcher.base.*
import com.dg.watcher.watching.saving.loadApkSizes
import hudson.model.AbstractProject
import hudson.util.Graph
import org.jfree.chart.ChartFactory.createLineChart
import org.jfree.chart.JFreeChart
import org.jfree.chart.axis.CategoryLabelPositions.UP_90
import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.plot.CategoryPlot
import org.jfree.chart.plot.PlotOrientation.VERTICAL
import org.jfree.chart.renderer.category.LineAndShapeRenderer
import org.kohsuke.stapler.StaplerRequest
import org.kohsuke.stapler.StaplerResponse
import java.awt.Color.RED
import java.awt.Color.white
import java.lang.System.currentTimeMillis
import java.text.NumberFormat


class HistoryGraph(private val project: AbstractProject<*, *>) : Graph(currentTimeMillis(), GRAPH_WIDTH, GRAPH_HEIGHT) {
    fun drawGraph(request: StaplerRequest, response: StaplerResponse) = doPng(request, response)

    fun drawGraphTooltips(request: StaplerRequest, response: StaplerResponse) = doMap(request, response)

    override fun createGraph(): JFreeChart = createLineGraph().also {
        setupGraphBackground(it)
        setupGraphSizesAxis(it)
        setupGraphBuildAxis(it)
        setupGraphSizesLine(it)
        setupGraphTooltips(it)
        setupGraphLinks(it)
    }

    private fun createLineGraph() = createLineChart(GRAPH_TITLE, GRAPH_X_AXIS, GRAPH_Y_AXIS,
            createGraphData(), VERTICAL, true, true, false)

    private fun createGraphData() = generateGraphDataSet(loadApkSizes(project.getLastBuild()))

    private fun setupGraphBackground(graph: JFreeChart) {
        graph.backgroundPaint = white
    }

    private fun setupGraphSizesAxis(graph: JFreeChart) {
        val sizesAxis = getPlot(graph).rangeAxis as NumberAxis

        val sizeInMegabyteFormat = NumberFormat.getInstance().apply {
            minimumFractionDigits = 1
            maximumFractionDigits = 1
        }

        sizesAxis.numberFormatOverride = sizeInMegabyteFormat
    }

    private fun setupGraphBuildAxis(graph: JFreeChart) {
        val buildAxis = getPlot(graph).domainAxis

        buildAxis.categoryLabelPositions = UP_90
    }

    private fun setupGraphSizesLine(graph: JFreeChart) {
        getRenderer(graph).apply {
            baseShapesVisible = true
            baseStroke = GRAPH_LINE
            setSeriesPaint(0, RED)
        }

        getPlot(graph).drawingSupplier = GRAPH_LINE_DOT
    }

    private fun setupGraphTooltips(graph: JFreeChart) {
        getRenderer(graph).baseToolTipGenerator = HistoryGraphTooltipGenerator()
    }

    private fun setupGraphLinks(graph: JFreeChart) {
        getRenderer(graph).baseItemURLGenerator = HistoryGraphUrlGenerator(project)
    }

    private fun getRenderer(graph: JFreeChart) = getPlot(graph).renderer as LineAndShapeRenderer

    private fun getPlot(graph: JFreeChart) = graph.plot as CategoryPlot
}