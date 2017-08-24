package com.dg.watcher.history.graph

import com.dg.watcher.base.Project
import org.jfree.chart.urls.CategoryURLGenerator
import org.jfree.data.category.CategoryDataset


class HistoryGraphUrlGenerator(private val project: Project) : CategoryURLGenerator {
    override fun generateURL(categoryDataSet: CategoryDataset, series: Int, itemIndex: Int) =
            project.getAbsoluteUrl() + getBuildNumber(categoryDataSet, itemIndex)

    private fun getBuildNumber(categoryDataSet: CategoryDataset, itemIndex: Int) =
            (categoryDataSet.getColumnKey(itemIndex) as String).replaceFirst("#", "")
}