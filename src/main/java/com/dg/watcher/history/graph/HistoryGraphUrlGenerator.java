package com.dg.watcher.history.graph;

import hudson.model.AbstractProject;
import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.data.category.CategoryDataset;


public class HistoryGraphUrlGenerator implements CategoryURLGenerator {
    private AbstractProject<?, ?> project;


    public HistoryGraphUrlGenerator(AbstractProject<?, ?> project) {
        this.project = project;
    }

    @Override
    public String generateURL(CategoryDataset categoryDataset, int series, int itemIndex) {
        return project.getAbsoluteUrl() + retrieveBuildNumber(categoryDataset, itemIndex);
    }

    private String retrieveBuildNumber(CategoryDataset categoryDataset, int itemIndex) {
        return ((String) categoryDataset.getColumnKey(itemIndex)).replaceFirst("#", "");
    }
}