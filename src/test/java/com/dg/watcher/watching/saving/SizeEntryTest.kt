package com.dg.watcher.watching.saving

import org.junit.Test
import org.hamcrest.Matchers.*
import org.junit.Assert.assertThat


class SizeEntryTest {
    @Test
    fun `Should compare two equal size entries`() {
        // GIVEN
        val entryA = SizeEntry("#1", 10000000L)
        val entryB = SizeEntry("#1", 10000000L)

        // THEN
        assertThat(entryA, `is`(equalTo(entryB)))
    }

    @Test
    fun `Should convert a size entry to string`() {
        // GIVEN
        val entry = SizeEntry("#1", 10000000L)

        // THEN
        assertThat(entry, hasToString("Name: #1 Size: 10000000 Byte"))
    }
}