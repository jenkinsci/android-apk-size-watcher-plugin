package com.dg.watcher

import com.dg.watcher.history.History
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import hudson.FilePath
import hudson.Launcher
import hudson.model.AbstractBuild
import hudson.model.AbstractProject
import hudson.model.BuildListener
import hudson.tasks.BuildStepMonitor.NONE
import org.hamcrest.Matchers.*
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.mockito.Mockito.verify


class PluginTest {
    @Rule @JvmField
    var tempDir = TemporaryFolder()


    @Before
    fun setUp() {
        createApkFolder()
        createApkFile()
    }

    @Test
    fun `Should not require a monitor service`() {
        assertThat(plugin().requiredMonitorService, `is`(equalTo(NONE)))
    }

    @Test
    fun `Should provide the history as the projects action`() {
        // WHEN
        val action = plugin().getProjectAction(mockProject())

        // THEN
        assertThat(action, `is`(instanceOf(History::class.java)))
    }

    @Test
    fun `Should update the history action when a build is performed`() {
        // GIVEN
        val action = mockAction()

        // WHEN
        plugin().perform(mockBuildIncludesAction(action), mockLauncher(), mockListener())

        // THEN
        verify(action).updateHistory()
    }

    private fun createApkFolder() = tempDir.newFolder("temp_apk_folder")

    private fun createApkFile() = tempDir.newFile("temp_apk_folder/debug.txt")

    private fun plugin() = Plugin(0f, "temp_apk_folder")

    private fun mockProjectIncludesAction(action: History) = mockProject().apply {
        whenever(getAction(History::class.java)).thenReturn(action)
    }

    private fun mockProject() = mock<AbstractProject<*, *>>().apply {
        whenever(getRootDir()).thenReturn(tempDir.root)
    }

    private fun mockBuildIncludesAction(action: History): AbstractBuild<*, *> {
        val project = mockProjectIncludesAction(action)
        val build = mockBuild()

        whenever(build.getProject()).thenReturn(project)

        return build
    }

    private fun mockBuild() = mock<AbstractBuild<*, *>>().apply {
        whenever(getWorkspace()).thenReturn(FilePath(tempDir.root))
    }

    private fun mockAction() = mock<History>()

    private fun mockLauncher() = mock<Launcher>()

    private fun mockListener() = mock<BuildListener>().apply {
        whenever(logger).thenReturn(mock())
    }
}