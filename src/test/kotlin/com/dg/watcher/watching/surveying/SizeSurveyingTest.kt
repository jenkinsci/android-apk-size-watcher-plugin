package com.dg.watcher.watching.surveying

import com.dg.watcher.watching.saving.SizeEntry
import com.dg.watcher.watching.surveying.SizeSurveyingResult.SIZE_THRESHOLD_EXCEEDED
import com.dg.watcher.watching.surveying.SizeSurveyingResult.SIZE_THRESHOLD_MET
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test


class SizeSurveyingTest {
    @Test
    fun `Should pass the size survey when there are no sizes recorded`() {
        // GIVEN
        val sizes = listOf<SizeEntry>()

        // THEN
        assertThat(surveySizes(sizes, 1f), `is`(equalTo(SIZE_THRESHOLD_MET)))
    }

    @Test
    fun `Should pass the size survey when only one size is recorded`() {
        // GIVEN
        val sizes = listOf(SizeEntry("#1", 10000000L))

        // THEN
        assertThat(surveySizes(sizes, 1f), `is`(equalTo(SIZE_THRESHOLD_MET)))
    }

    @Test
    fun `Should pass the size survey when the size difference is below the threshold`() {
        // GIVEN
        val sizes = listOf(SizeEntry("#1", 10000000L), SizeEntry("#2", 11000000L))

        // THEN
        assertThat(surveySizes(sizes, 2f), `is`(equalTo(SIZE_THRESHOLD_MET)))
    }

    @Test
    fun `Should pass the size survey when the size difference is exactly the threshold`() {
        // GIVEN
        val sizes = listOf(SizeEntry("#1", 10000000L), SizeEntry("#2", 12000000L))

        // THEN
        assertThat(surveySizes(sizes, 2f), `is`(equalTo(SIZE_THRESHOLD_MET)))
    }

    @Test
    fun `Should fail the size survey when the size difference exceeds the threshold`() {
        // GIVEN
        val sizes = listOf(SizeEntry("#1", 10000000L), SizeEntry("#2", 14000000L))

        // THEN
        assertThat(surveySizes(sizes, 2f), `is`(equalTo(SIZE_THRESHOLD_EXCEEDED)))
    }
}