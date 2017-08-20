package com.dg.watcher.history.graph

import org.jfree.chart.labels.CategoryToolTipGenerator
import org.jfree.data.category.CategoryDataset
import com.dg.watcher.base.GRAPH_TOOLTIP
import java.lang.String.format
import java.util.Locale.ENGLISH


class HistoryGraphTooltipGenerator : CategoryToolTipGenerator {
    override fun generateToolTip(categoryDataset: CategoryDataset, series: Int, itemIndex: Int): String {
        val buildName = categoryDataset.getColumnKey(itemIndex).toString()
        val apkSizeInMb = categoryDataset.getValue(series, itemIndex).toFloat()

        return format(ENGLISH, GRAPH_TOOLTIP, buildName, apkSizeInMb)
    }
}