package com.dg.watcher.history.graph;

import com.dg.watcher.watching.saving.SizeEntry;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.ArrayList;
import java.util.List;
import static com.dg.watcher.base.Const.*;


public class HistoryGraphDataSetGenerator {
    public static DefaultCategoryDataset generateGraphDataSet(ArrayList<SizeEntry> entries) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        for(SizeEntry entry : cutToMaximumEntryCount(entries)) {
            float sizeInMegaByte = entry.getSizeInByte()
                    / CONVERSION_FACTOR_BYTE_TO_MEGABYTE;

            dataSet.addValue(sizeInMegaByte, GRAPH_LEGEND, entry.getBuildName());
        }

        return dataSet;
    }

    private static List<SizeEntry> cutToMaximumEntryCount(ArrayList<SizeEntry> entries) {
        int indexOfFirstIncludedEntry = entries.size() - GRAPH_MAX_ENTRY_COUNT;

        if(indexOfFirstIncludedEntry < 0) indexOfFirstIncludedEntry = 0;

        return entries.subList(indexOfFirstIncludedEntry, entries.size());
    }
}