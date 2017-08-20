package com.dg.watcher.history.graph;

import hudson.util.DataSetBuilder;
import org.jfree.data.category.CategoryDataset;
import org.junit.Test;
import static com.dg.watcher.base.Const.GRAPH_LEGEND;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class HistoryGraphTooltipGeneratorTest {
    @Test
    public void should_generate_a_tooltip_consisting_of_the_builds_name_and_the_size_of_the_apk() {
        // GIVEN
        CategoryDataset entry = createDataSetWithSizeEntry("#99", 1.5f);

        // WHEN
        String tooltip = generator().generateToolTip(entry, 0, 0);

        // THEN
        assertThat(tooltip, is(equalTo("Build #99: 1.5 Megabyte")));
    }

    private HistoryGraphTooltipGenerator generator() {
        return new HistoryGraphTooltipGenerator();
    }

    private CategoryDataset createDataSetWithSizeEntry(String buildName, float apkSizeInMb) {
        DataSetBuilder<String, String> dataSet = new DataSetBuilder<>();
        dataSet.add(apkSizeInMb, GRAPH_LEGEND, buildName);

        return dataSet.build();
    }
}