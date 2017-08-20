package com.dg.watcher.history.graph;

import com.dg.watcher.watching.saving.SizeEntry;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.Test;
import java.util.ArrayList;
import static com.dg.watcher.history.graph.HistoryGraphDataSetGenerator.generateGraphDataSet;
import static com.dg.watcher.base.Const.CONVERSION_FACTOR_BYTE_TO_MEGABYTE;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class HistoryGraphDataSetGeneratorTest {
    @Test
    public void should_include_all_20_entries_in_the_generated_data_set() {
        // GIVEN
        ArrayList<SizeEntry> entries = new ArrayList<>();
        for(int i = 0; i < 20; ++i) entries.add(new SizeEntry("#" + i, 0L));

        // WHEN
        DefaultCategoryDataset dataSet = generateGraphDataSet(entries);

        // THEN
        assertThat(retrieveEntries(dataSet), is(equalTo(entries)));
    }

    @Test
    public void should_include_the_last_20_entries_in_the_generated_data_set() {
        // GIVEN
        ArrayList<SizeEntry> entries = new ArrayList<>();
        for(int i = 0; i < 35; ++i) entries.add(new SizeEntry("#" + i, 0L));

        // WHEN
        DefaultCategoryDataset dataSet = generateGraphDataSet(entries);

        // THEN
        assertThat(retrieveEntries(dataSet), is(equalTo(entries.subList(15, 35))));
    }

    @Test
    public void should_use_a_entries_size_in_megabyte_as_its_row_value() {
        // GIVEN
        ArrayList<SizeEntry> entries = new ArrayList<>();
        entries.add(new SizeEntry("#1", 10000000L));

        // WHEN
        DefaultCategoryDataset dataSet = generateGraphDataSet(entries);

        // THEN
        float rowValue = dataSet.getValue(0, 0).floatValue();

        assertThat(rowValue, is(10000000L / CONVERSION_FACTOR_BYTE_TO_MEGABYTE));
    }

    @Test
    public void should_use_a_entries_build_name_as_its_column_key() {
        // GIVEN
        ArrayList<SizeEntry> entries = new ArrayList<>();
        entries.add(new SizeEntry("#1", 10000000L));

        // WHEN
        DefaultCategoryDataset dataSet = generateGraphDataSet(entries);

        // THEN
        String columnKey = dataSet.getColumnKey(0).toString();

        assertThat(columnKey, is(equalTo("#1")));
    }

    private ArrayList<SizeEntry> retrieveEntries(DefaultCategoryDataset dataSet) {
        ArrayList<SizeEntry> entries = new ArrayList<>();

        for(int i = 0; i < dataSet.getColumnCount(); ++i) {
            String buildName = dataSet.getColumnKey(i).toString();

            float apkSizeInMb = dataSet.getValue(0, i).floatValue();
            long  apkSizeInByte = (long) (apkSizeInMb
                    * CONVERSION_FACTOR_BYTE_TO_MEGABYTE);

            entries.add(new SizeEntry(buildName, apkSizeInByte));
        }

        return entries;
    }
}