package com.dg.watcher.base.extension

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import hudson.FilePath
import org.junit.Assert.assertFalse
import org.junit.Test


class FilePathExtensionTest {
    @Test
    fun `A null file path doesn't exist per default`() {
        // GIVEN
        val filePath: FilePath? = null

        // THEN
        assertFalse(filePath.exists())
    }

    @Test
    fun `A non null file path checks for its existence`() {
        // GIVEN
        val filePath: FilePath? = mock()

        // WHEN
        filePath.exists()

        // THEN
        verify(filePath)?.exists()
    }
}