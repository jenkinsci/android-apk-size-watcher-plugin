package com.dg.watcher.base.extension

import com.dg.watcher.base.Build
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import hudson.FilePath
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test


class BuildExtensionTest {
    @Test
    fun `Should provide a shortcut to get a file inside the builds's workspace`() {
        // GIVEN
        val file = mock<FilePath>()
        val workspace = mock<FilePath> { on { child("test/debug.apk") } doReturn file }
        val build = mock<Build> { on { getWorkspace() } doReturn workspace }

        // THEN
        assertThat(build.getFileInWorkspace("test/debug.apk"), `is`(equalTo(file)))
    }
}