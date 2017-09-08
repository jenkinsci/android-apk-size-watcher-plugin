package com.dg.watcher.watching.saving

import com.dg.watcher.base.Build
import com.dg.watcher.base.Project
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.Matchers.*
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import hudson.FilePath


class SizeSavingTest {
    @Rule @JvmField
    val tempDir = TemporaryFolder()


    @Test
    fun `Should load the saved sizes`() {
        // GIVEN
        saveApkSize(mockApk(sizeInByte = 10000000L), mockBuild(name = "#1"))
        saveApkSize(mockApk(sizeInByte = 11000000L), mockBuild(name = "#2"))

        // WHEN
        val sizes = loadApkSizes(mockProject())

        // THEN
        assertThat(sizes, hasSize(2))
        assertThat(sizes.first().buildName,  `is`(equalTo("#1")))
        assertThat(sizes.first().sizeInByte, `is`(equalTo(10000000L)))
        assertThat(sizes.last().buildName,   `is`(equalTo("#2")))
        assertThat(sizes.last().sizeInByte,  `is`(equalTo(11000000L)))
    }

    @Test
    fun `Should not load any sizes when there are none saved`() =
            assertThat(loadApkSizes(mockProject()), hasSize(0))

    private fun mockBuild(name: String) = mockBuild().apply {
        whenever(getDisplayName()).thenReturn(name)
    }

    private fun mockBuild() = mock<Build>().apply {
        val project = mockProject()

        whenever(getProject()).thenReturn(project)
    }

    private fun mockProject() = mock<Project>().apply {
        whenever(getRootDir()).thenReturn(tempDir.root)
    }

    private fun mockApk(sizeInByte: Long) = mock<FilePath>().apply {
        whenever(length()).thenReturn(sizeInByte)
    }
}