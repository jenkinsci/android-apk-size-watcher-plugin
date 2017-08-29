package com.dg.watcher.history.graph

import com.dg.watcher.base.GRAPH_LEGEND
import hudson.util.DataSetBuilder
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test


class HistoryGraphTooltipGeneratorTest {
    @Test
    fun `Should generate a tooltip consisting of the builds name and the size of the apk`() {
        // GIVEN
        val entry = createDataSetWithSizeEntry("#99", 1.5f)

        // WHEN
        val tooltip = HistoryGraphTooltipGenerator().generateToolTip(entry, 0, 0)

        // THEN
        assertThat(tooltip, `is`(equalTo("Build #99: 1.5 Megabytes")))
    }

    private fun createDataSetWithSizeEntry(buildName: String, apkSizeInMb: Float) = DataSetBuilder<String, String>().run {
        add(apkSizeInMb, GRAPH_LEGEND, buildName)

        build()
    }
}