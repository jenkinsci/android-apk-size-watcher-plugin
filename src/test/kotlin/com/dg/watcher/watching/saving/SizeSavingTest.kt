package com.dg.watcher.watching.saving

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import hudson.model.AbstractBuild
import hudson.model.AbstractProject
import org.hamcrest.Matchers.*
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File


class SizeSavingTest {
    @Rule @JvmField
    var tempDir = TemporaryFolder()


    @Test
    fun `Should load the saved sizes`() {
        // GIVEN
        saveApkSize(mockApkWithSizeInByte(10000000L), mockBuildWithName("#1"))
        saveApkSize(mockApkWithSizeInByte(11000000L), mockBuildWithName("#2"))

        // WHEN
        val sizes = loadApkSizes(mockBuild())

        // THEN
        assertThat(sizes, hasSize(2))
        assertThat(sizes.first().buildName,  `is`(equalTo("#1")))
        assertThat(sizes.first().sizeInByte, `is`(equalTo(10000000L)))
        assertThat(sizes.last().buildName,   `is`(equalTo("#2")))
        assertThat(sizes.last().sizeInByte,  `is`(equalTo(11000000L)))
    }

    @Test
    fun `Should not load any sizes when there are none saved`() =
            assertThat(loadApkSizes(mockBuild()), hasSize(0))

    private fun mockBuildWithName(name: String) = mockBuild().apply {
        whenever(getDisplayName()).thenReturn(name)
    }

    private fun mockBuild() = mock<AbstractBuild<*, *>>().apply {
        val project: AbstractProject<*, *> = mock {
            on { getRootDir() } doReturn tempDir.root
        }

        whenever(getProject()).thenReturn(project)
    }

    private fun mockApkWithSizeInByte(sizeInByte: Long) = mock<File>().apply {
        whenever(length()).thenReturn(sizeInByte)
    }
}