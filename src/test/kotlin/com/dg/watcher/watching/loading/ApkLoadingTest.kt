package com.dg.watcher.watching.loading

import com.dg.watcher.base.Build
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import hudson.FilePath
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File.separator


class ApkLoadingTest {
    @Rule @JvmField
    val tempDir = TemporaryFolder()


    @Test
    fun `Should return null when the workspace is non existent`() =
            assertNull(loadApk(mockBuild(workspace = null)))

    @Test
    fun `Should return null when the default folder is non existent`() =
            assertNull(loadApk(mockBuild()))

    @Test
    fun `Should return null when the specified folder is non existent`() =
            assertNull(loadApk(mockBuild(), "temp_apk_folder"))

    @Test
    fun `Should return null when the apk in the default folder is non existent`() {
        // GIVEN
        createApkFolder("app", "build", "outputs", "apk")

        // THEN
        assertNull(loadApk(mockBuild()))
    }

    @Test
    fun `Should return null when the apk in the specified folder is non existent`() {
        // GIVEN
        createApkFolder("temp_apk_folder")

        // THEN
        assertNull(loadApk(mockBuild(), "temp_apk_folder"))
    }

    @Test
    fun `Should load the apk from the default folder`() {
        // GIVEN
        createApkFolder("app", "build", "outputs", "apk")
        createApkFile("app/build/outputs/apk", "debug.apk")

        // THEN
        assertNotNull(loadApk(mockBuild()))
    }

    @Test
    fun `Should load the apk from the specified folder`() {
        // GIVEN
        createApkFolder("temp_apk_folder")
        createApkFile("temp_apk_folder", "debug.apk")

        // THEN
        assertNotNull(loadApk(mockBuild(), "temp_apk_folder"))
    }

    @Test
    fun `Should never load the apk from a nested folder`() {
        // GIVEN
        createApkFolder("apk_folder", "nested_folder")
        createApkFile("apk_folder/nested_folder", "debug.apk")

        // THEN
        assertNull(loadApk(mockBuild(), "apk_folder"))
    }

    private fun createApkFolder(vararg folders: String) = tempDir.newFolder(*folders)

    private fun createApkFile(folder: String, fileName: String) = tempDir.newFile("$folder$separator$fileName")

    private fun mockBuild(workspace: FilePath? = FilePath(tempDir.root)) = mock<Build>().apply {
        whenever(getWorkspace()).thenReturn(workspace)
    }
}