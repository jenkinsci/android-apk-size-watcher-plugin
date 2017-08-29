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
import java.io.File


class SizeSavingTest {
    @Rule @JvmField
    val tempDir = TemporaryFolder()


    @Test
    fun `Should load the saved sizes`() {
        // GIVEN
        saveApkSize(mockApkWithSizeInByte(10000000L), mockBuildWithName("#1"))
        saveApkSize(mockApkWithSizeInByte(11000000L), mockBuildWithName("#2"))

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

    private fun mockBuildWithName(name: String) = mockBuild().apply {
        whenever(getDisplayName()).thenReturn(name)
    }

    private fun mockBuild() = mock<Build>().apply {
        val project = mockProject()

        whenever(getProject()).thenReturn(project)
    }

    private fun mockProject() = mock<Project>().apply {
        whenever(getRootDir()).thenReturn(tempDir.root)
    }

    private fun mockApkWithSizeInByte(sizeInByte: Long) = mock<File>().apply {
        whenever(length()).thenReturn(sizeInByte)
    }
}