package com.dg.watcher.watching.saving

import com.dg.watcher.watching.saving.SizeSaving.loadApkSizes
import com.dg.watcher.watching.saving.SizeSaving.saveApkSize
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
    fun `Should load the saved size entries`() {
        // GIVEN
        saveApkSize(mockApkWithSizeInByte(10000000L), mockBuildWithName("#1"))
        saveApkSize(mockApkWithSizeInByte(11000000L), mockBuildWithName("#2"))

        // WHEN
        val loadedSizes = loadApkSizes(mockBuild())

        // THEN
        assertThat(loadedSizes, hasSize(2))
        assertThat(loadedSizes.first().buildName,  `is`(equalTo("#1")))
        assertThat(loadedSizes.first().sizeInByte, `is`(equalTo(10000000L)))
        assertThat(loadedSizes.last().buildName,   `is`(equalTo("#2")))
        assertThat(loadedSizes.last().sizeInByte,  `is`(equalTo(11000000L)))
    }

    @Test
    fun `Should not load any size entry when there are none saved`() =
            assertThat(loadApkSizes(mockBuild()), hasSize(0))

    private fun mockBuildWithName(buildName: String) = mockBuild().apply {
        whenever(getDisplayName()).thenReturn(buildName)
    }

    private fun mockBuild(): AbstractBuild<*, *> {
        val project: AbstractProject<*, *> = mock { on { getRootDir() } doReturn tempDir.root}

        return mock { on { getProject() } doReturn project}
    }

    private fun mockApkWithSizeInByte(sizeInByte: Long) = mock<File>().apply {
        whenever(length()).thenReturn(sizeInByte)
    }
}