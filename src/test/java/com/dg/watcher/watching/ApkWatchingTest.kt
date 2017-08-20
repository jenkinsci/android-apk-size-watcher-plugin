package com.dg.watcher.watching

import com.dg.watcher.base.Const.BUILD_ALLOWED
import com.dg.watcher.base.Const.BUILD_FORBIDDEN
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import hudson.FilePath
import hudson.model.AbstractBuild
import hudson.model.AbstractProject
import org.apache.commons.io.FileUtils.cleanDirectory
import org.apache.commons.io.FileUtils.writeByteArrayToFile
import org.hamcrest.Matchers.equalTo
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File


class ApkWatchingTest {
    @Rule @JvmField
    var tempDir = TemporaryFolder()


    @Before
    fun setUp() {
        createApkFolder()
    }

    @Test
    fun `Should allow the initial build without an generated apk`() {
        // GIVEN
        val build = mockBuildWithoutAnApk()

        // WHEN
        val result = watchApkSize(build)

        // THEN
        assertThat(result, `is`(equalTo(BUILD_ALLOWED)))
    }

    @Test
    fun `Should allow the initial build with an generated apk`() {
        // GIVEN
        val build = mockBuildWithAnApkOfSizeInByte(10000000L)

        // WHEN
        val result = watchApkSize(build, 1f)

        // THEN
        assertThat(result, `is`(equalTo(BUILD_ALLOWED)))
    }

    @Test
    fun `Should allow the build when the generated apk meets the size threshold`() {
        // GIVEN
        watchApkSize(mockBuildWithAnApkOfSizeInByte(10000000L))
        val build = mockBuildWithAnApkOfSizeInByte(11000000L)

        // WHEN
        val result = watchApkSize(build, 1f)

        // THEN
        assertThat(result, `is`(equalTo(BUILD_ALLOWED)))
    }

    @Test
    fun `Should forbid the build when the generated apk exceeds the size threshold`() {
        // GIVEN
        watchApkSize(mockBuildWithAnApkOfSizeInByte(10000000L))
        val build = mockBuildWithAnApkOfSizeInByte(12000000L)

        // WHEN
        val result = watchApkSize(build, 1f)

        // THEN
        assertThat(result, `is`(equalTo(BUILD_FORBIDDEN)))
    }

    private fun watchApkSize(build: AbstractBuild<*, *>, thresholdInMb: Float = 0f) =
            ApkWatching.watchApkSize(build, mock(), thresholdInMb, "")

    private fun mockBuild(): AbstractBuild<*, *> {
        val project: AbstractProject<*, *> = mock()
        whenever(project.getRootDir()).thenReturn(tempDir.root)

        val build: AbstractBuild<*, *> = mock()
        whenever(build.getProject()).thenReturn(project)
        whenever(build.getWorkspace()).thenReturn(FilePath(tempDir.root))

        return build
    }

    private fun mockBuildWithoutAnApk() = mockBuild()

    private fun mockBuildWithAnApkOfSizeInByte(apkSizeInByte: Long) = mockBuild().also {
        createApkForBuild(apkSizeInByte)
    }

    private fun createApkFolder() = tempDir.newFolder("app", "build", "outputs", "apk")

    private fun createApkForBuild(apkSizeInByte: Long) {
        deleteOldApk()

        createNewApk(apkSizeInByte)
    }

    private fun deleteOldApk() {
        cleanDirectory(File(tempDir.root.absolutePath + "/app/build/outputs/apk"))
    }

    private fun createNewApk(apkSizeInByte: Long) {
        val apk = tempDir.newFile("app/build/outputs/apk/debug.apk")

        writeByteArrayToFile(apk, ByteArray(apkSizeInByte.toInt()))
    }
}