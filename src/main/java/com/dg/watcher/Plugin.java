package com.dg.watcher;

import com.dg.watcher.history.History;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import org.kohsuke.stapler.DataBoundConstructor;
import java.io.IOException;
import static com.dg.watcher.watching.ApkWatching.watchApkSize;
import static hudson.tasks.BuildStepMonitor.NONE;


public class Plugin extends Notifier {
    private float  thresholdInMb;
    private String customPathToApk;


    @DataBoundConstructor
    public Plugin(float thresholdInMb, String customPathToApk) {
        this.thresholdInMb = thresholdInMb;
        this.customPathToApk = customPathToApk;
    }

    // Used in config.jelly
    public float getThresholdInMb() {
        return thresholdInMb;
    }

    // Used in config.jelly
    public String getCustomPathToApk() {
        return customPathToApk;
    }

    @Override
    public PluginDescriptor getDescriptor() {
        return (PluginDescriptor) super.getDescriptor();
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return NONE;
    }

    @Override
    public Action getProjectAction(AbstractProject<?, ?> project) {
        return new History(project);
    }

    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
        boolean buildPermitted = watchApkSize(build, listener.getLogger(), thresholdInMb, customPathToApk);

        updateActiveProjectAction(build.getProject());

        return buildPermitted;
    }

    private void updateActiveProjectAction(AbstractProject<?, ?> project) {
        getActiveProjectAction(project).updateHistory();
    }

    private History getActiveProjectAction(AbstractProject<?, ?> project) {
        return project.getAction(History.class);
    }
}