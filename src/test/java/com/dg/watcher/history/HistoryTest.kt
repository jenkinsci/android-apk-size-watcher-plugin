package com.dg.watcher.history

import com.nhaarman.mockito_kotlin.mock
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test


class HistoryTest {
    @Test
    fun `Should specify the shown icon`() =
            assertThat(history().iconFileName, `is`(equalTo("graph.gif")))

    @Test
    fun `Should specify the shown label`() =
            assertThat(history().displayName, `is`(equalTo("Apk History")))

    @Test
    fun `Should specify the used domain`() =
            assertThat(history().urlName, `is`(equalTo("apk_history")))

    private fun history() = History(mock())
}