package com.dg.watcher.history.graph

import com.dg.watcher.base.Const.GRAPH_LEGEND
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import hudson.util.DataSetBuilder
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test


class HistoryGraphUrlGeneratorTest {
    @Test
    fun `Should generate a url linking to the build of the size entry`() {
        // GIVEN
        val entry = createDataSetWithSizeEntry("#99")

        // WHEN
        val url = generator().generateURL(entry, 0, 0)

        // THEN
        assertThat(url, `is`(equalTo(".../jenkins/job/test/99")))
    }

    private fun generator() = HistoryGraphUrlGenerator(
            mock { on { getAbsoluteUrl() } doReturn ".../jenkins/job/test/" })

    private fun createDataSetWithSizeEntry(buildName: String) = DataSetBuilder<String, String>().run {
        add(0f, GRAPH_LEGEND, buildName)

        build()
    }
}