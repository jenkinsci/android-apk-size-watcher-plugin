package com.dg.watcher.history

import com.dg.watcher.base.Project
import com.dg.watcher.history.graph.HistoryGraph
import hudson.model.Action
import org.kohsuke.stapler.StaplerRequest
import org.kohsuke.stapler.StaplerResponse


class History(val project: Project) : Action {
    private var graph: HistoryGraph? = null

    init {
        updateHistory()
    }

    override fun getIconFileName() = "graph.gif"

    override fun getDisplayName() = "Apk Size History"

    override fun getUrlName() = "apk_size_history"

    fun updateHistory() {
        graph = HistoryGraph(project)
    }

    // Used in index.jelly
    fun doHistory(request: StaplerRequest, response: StaplerResponse) {
        graph?.drawGraph(request, response)
    }

    // Used in index.jelly
    fun doHistoryTooltips(request: StaplerRequest, response: StaplerResponse) {
        graph?.drawGraphTooltips(request, response)
    }
}