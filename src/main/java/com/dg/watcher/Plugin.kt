package com.dg.watcher

import com.dg.watcher.history.History
import com.dg.watcher.watching.watchApkSize
import hudson.Launcher
import hudson.model.AbstractBuild
import hudson.model.AbstractProject
import hudson.model.BuildListener
import hudson.tasks.BuildStepMonitor.NONE
import hudson.tasks.Notifier
import org.kohsuke.stapler.DataBoundConstructor


class Plugin @DataBoundConstructor constructor(val thresholdInMb: Float, val customPathToApk: String) : Notifier() {
    override fun getDescriptor() = super.getDescriptor() as PluginDescriptor

    override fun getRequiredMonitorService() = NONE

    override fun getProjectAction(project: AbstractProject<*, *>) = History(project)

    override fun perform(build: AbstractBuild<*, *>, launcher: Launcher, listener: BuildListener): Boolean {
        val buildPermitted = watchApkSize(build, listener.logger, thresholdInMb, customPathToApk)

        updateActiveProjectAction(build.getProject())

        return buildPermitted
    }

    private fun updateActiveProjectAction(project: AbstractProject<*, *>) =
            getActiveProjectAction(project).updateHistory()

    private fun getActiveProjectAction(project: AbstractProject<*, *>) =
            project.getAction(History::class.java)
}