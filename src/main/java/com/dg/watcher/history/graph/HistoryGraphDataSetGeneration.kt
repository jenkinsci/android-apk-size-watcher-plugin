package com.dg.watcher.history.graph

import com.dg.watcher.base.CONVERSION_FACTOR_BYTE_TO_MEGABYTE
import com.dg.watcher.base.GRAPH_LEGEND
import com.dg.watcher.base.GRAPH_MAX_ENTRY_COUNT
import com.dg.watcher.watching.saving.SizeEntry
import org.jfree.data.category.DefaultCategoryDataset


fun generateGraphDataSet(entries: List<SizeEntry>) = DefaultCategoryDataset().apply {
    for((buildName, sizeInByte) in cutToMaximumEntryCount(entries)) {
        val sizeInMegaByte = sizeInByte / CONVERSION_FACTOR_BYTE_TO_MEGABYTE

        addValue(sizeInMegaByte, GRAPH_LEGEND, buildName)
    }
}

private fun cutToMaximumEntryCount(entries: List<SizeEntry>): List<SizeEntry> {
    var indexOfFirstIncludedEntry = entries.size - GRAPH_MAX_ENTRY_COUNT

    if(indexOfFirstIncludedEntry < 0) indexOfFirstIncludedEntry = 0

    return entries.subList(indexOfFirstIncludedEntry, entries.size)
}