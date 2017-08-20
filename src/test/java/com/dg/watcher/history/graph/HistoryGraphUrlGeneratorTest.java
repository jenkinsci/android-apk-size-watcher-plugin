package com.dg.watcher.history.graph;

import hudson.model.AbstractProject;
import hudson.util.DataSetBuilder;
import org.jfree.data.category.CategoryDataset;
import org.junit.Test;
import static com.dg.watcher.base.Const.GRAPH_LEGEND;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class HistoryGraphUrlGeneratorTest {
    @Test
    public void should_generate_a_url_linking_to_the_build_of_the_size_entry() {
        // GIVEN
        CategoryDataset entry = createDataSetWithSizeEntry("#99");

        // WHEN
        String url = generator().generateURL(entry, 0, 0);

        // THEN
        assertThat(url, is(equalTo(".../jenkins/job/test/99")));
    }

    private HistoryGraphUrlGenerator generator() {
        AbstractProject project = mock(AbstractProject.class);
        when(project.getAbsoluteUrl()).thenReturn(".../jenkins/job/test/");

        return new HistoryGraphUrlGenerator(project);
    }

    private CategoryDataset createDataSetWithSizeEntry(String buildName) {
        DataSetBuilder<String, String> dataSet = new DataSetBuilder<>();
        dataSet.add(0f, GRAPH_LEGEND, buildName);

        return dataSet.build();
    }
}