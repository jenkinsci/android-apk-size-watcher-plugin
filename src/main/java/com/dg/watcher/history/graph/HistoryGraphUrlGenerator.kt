package com.dg.watcher.history.graph

import hudson.model.AbstractProject
import org.jfree.chart.urls.CategoryURLGenerator
import org.jfree.data.category.CategoryDataset


class HistoryGraphUrlGenerator(private val project: AbstractProject<*, *>) : CategoryURLGenerator {
    override fun generateURL(categoryDataset: CategoryDataset, series: Int, itemIndex: Int) =
            project.getAbsoluteUrl() + retrieveBuildNumber(categoryDataset, itemIndex)

    private fun retrieveBuildNumber(categoryDataset: CategoryDataset, itemIndex: Int) =
            (categoryDataset.getColumnKey(itemIndex) as String).replaceFirst("#", "")
}