package com.dg.watcher.history.graph;

import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.data.category.CategoryDataset;
import static com.dg.watcher.base.Const.GRAPH_TOOLTIP;
import static java.lang.String.format;
import static java.util.Locale.ENGLISH;


public class HistoryGraphTooltipGenerator implements CategoryToolTipGenerator {
    @Override
    public String generateToolTip(CategoryDataset categoryDataset, int series, int itemIndex) {
        String buildName = categoryDataset.getColumnKey(itemIndex).toString();
        float  apkSizeInMb = categoryDataset.getValue(series, itemIndex).floatValue();

        return format(ENGLISH, GRAPH_TOOLTIP, buildName, apkSizeInMb);
    }
}