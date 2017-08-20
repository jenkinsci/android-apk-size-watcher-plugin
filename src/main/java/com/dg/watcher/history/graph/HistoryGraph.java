package com.dg.watcher.history.graph;

import hudson.model.AbstractProject;
import hudson.util.Graph;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import java.io.IOException;
import java.text.NumberFormat;
import static com.dg.watcher.history.graph.HistoryGraphDataSetGenerator.generateGraphDataSet;
import static com.dg.watcher.base.Const.*;
import static com.dg.watcher.watching.saving.SizeSaving.loadApkSizes;
import static java.awt.Color.RED;
import static java.awt.Color.white;
import static java.lang.System.currentTimeMillis;
import static org.jfree.chart.ChartFactory.createLineChart;
import static org.jfree.chart.axis.CategoryLabelPositions.UP_90;
import static org.jfree.chart.plot.PlotOrientation.VERTICAL;


public class HistoryGraph extends Graph {
    private AbstractProject<?, ?> project;


    public HistoryGraph(AbstractProject<?, ?> project) {
        super(currentTimeMillis(), GRAPH_WIDTH, GRAPH_HEIGHT);

        this.project = project;
    }

    public void drawGraph(StaplerRequest request, StaplerResponse response) throws IOException {
        doPng(request, response);
    }

    public void drawGraphTooltips(StaplerRequest request, StaplerResponse response) throws IOException {
        doMap(request, response);
    }

    @Override
    protected JFreeChart createGraph() {
        JFreeChart graph = createLineGraph();

        setupGraphBackground(graph);
        setupGraphSizesAxis(graph);
        setupGraphBuildAxis(graph);
        setupGraphSizesLine(graph);
        setupGraphTooltips(graph);
        setupGraphLinks(graph);

        return graph;
    }

    private JFreeChart createLineGraph() {
        return createLineChart(GRAPH_TITLE, GRAPH_X_AXIS, GRAPH_Y_AXIS,
                createGraphData(), VERTICAL, true, true, false);
    }

    private DefaultCategoryDataset createGraphData() {
        return generateGraphDataSet(loadApkSizes(project.getLastBuild()));
    }

    private void setupGraphBackground(JFreeChart graph) {
        graph.setBackgroundPaint(white);
    }

    private void setupGraphSizesAxis(JFreeChart graph) {
        NumberFormat sizeInMegabyteFormat = NumberFormat.getInstance();
        sizeInMegabyteFormat.setMinimumFractionDigits(1);
        sizeInMegabyteFormat.setMaximumFractionDigits(1);

        ((NumberAxis) getPlot(graph).getRangeAxis())
                .setNumberFormatOverride(sizeInMegabyteFormat);
    }

    private void setupGraphBuildAxis(JFreeChart graph) {
        getPlot(graph).getDomainAxis().setCategoryLabelPositions(UP_90);
    }

    private void setupGraphSizesLine(JFreeChart graph) {
        LineAndShapeRenderer renderer = getRenderer(graph);
        renderer.setBaseShapesVisible(true);
        renderer.setBaseStroke(GRAPH_LINE);
        renderer.setSeriesPaint(0, RED);

        getPlot(graph).setDrawingSupplier(GRAPH_LINE_DOT);
    }

    private void setupGraphTooltips(JFreeChart graph) {
        getRenderer(graph).setBaseToolTipGenerator(new HistoryGraphTooltipGenerator());
    }

    private void setupGraphLinks(JFreeChart graph) {
        getRenderer(graph).setBaseItemURLGenerator(new HistoryGraphUrlGenerator(project));
    }

    private LineAndShapeRenderer getRenderer(JFreeChart graph) {
        return (LineAndShapeRenderer) getPlot(graph).getRenderer();
    }

    private CategoryPlot getPlot(JFreeChart graph) {
        return (CategoryPlot) graph.getPlot();
    }
}