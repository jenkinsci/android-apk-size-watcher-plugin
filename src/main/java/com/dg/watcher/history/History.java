package com.dg.watcher.history;

import com.dg.watcher.history.graph.HistoryGraph;
import hudson.model.AbstractProject;
import hudson.model.Action;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import java.io.IOException;


public class History implements Action {
    private AbstractProject<?, ?> project;
    private HistoryGraph graph;


    public History(AbstractProject<?, ?> project) {
        this.project = project;

        updateHistory();
    }

    public AbstractProject<?, ?> getProject() {
        return project;
    }

    @Override
    public String getIconFileName() {
        return "graph.gif";
    }

    @Override
    public String getDisplayName() {
        return "Apk History";
    }

    @Override
    public String getUrlName() {
        return "apk_history";
    }

    public void updateHistory() {
        graph = new HistoryGraph(project);
    }

    // Used in index.jelly
    public void doHistory(StaplerRequest request, StaplerResponse response) throws IOException {
        graph.drawGraph(request, response);
    }

    // Used in index.jelly
    public void doHistoryTooltips(StaplerRequest request, StaplerResponse response) throws IOException {
        graph.drawGraphTooltips(request, response);
    }
}