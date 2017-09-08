package com.dg.watcher.base.extension

import com.dg.watcher.base.Project
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import hudson.FilePath
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test


class ProjectExtensionTest {
    @Test
    fun `Should provide a shortcut to get a file inside the project's workspace`() {
        // GIVEN
        val file = mock<FilePath>()
        val workspace = mock<FilePath> { on { child("test/debug.apk") } doReturn file }
        val project = mock<Project> { on { getSomeWorkspace() } doReturn workspace }

        // THEN
        assertThat(project.getFileInWorkspace("test/debug.apk"), `is`(equalTo(file)))
    }
}