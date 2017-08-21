package com.dg.watcher.history.graph

import hudson.model.AbstractProject
import org.jfree.chart.urls.CategoryURLGenerator
import org.jfree.data.category.CategoryDataset


class HistoryGraphUrlGenerator(private val project: AbstractProject<*, *>) : CategoryURLGenerator {
    override fun generateURL(categoryDataSet: CategoryDataset, series: Int, itemIndex: Int) =
            project.getAbsoluteUrl() + retrieveBuildNumber(categoryDataSet, itemIndex)

    private fun retrieveBuildNumber(categoryDataSet: CategoryDataset, itemIndex: Int) =
            (categoryDataSet.getColumnKey(itemIndex) as String).replace("#", "")
}