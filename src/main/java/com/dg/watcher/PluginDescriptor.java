package com.dg.watcher;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import hudson.util.FormValidation;
import org.kohsuke.stapler.AncestorInPath;
import org.kohsuke.stapler.QueryParameter;
import static com.dg.watcher.validation.InputValidationKt.validateCustomPathToApk;
import static com.dg.watcher.validation.InputValidationKt.validateThresholdInMb;


@Extension
public class PluginDescriptor extends BuildStepDescriptor<Publisher> {
    public PluginDescriptor() {
        super(Plugin.class);
    }

    @Override
    public boolean isApplicable(Class type) {
        return true;
    }

    @Override
    public String getDisplayName() {
        return "Watch over the changing size of your .apk file";
    }

    // Used for validation in config.jelly
    public FormValidation doCheckThresholdInMb(@QueryParameter String value) {
        return validateThresholdInMb(value);
    }

    // Used for validation in config.jelly
    public FormValidation doCheckCustomPathToApk(@QueryParameter String value, @AncestorInPath AbstractProject<?, ?> project) {
        return validateCustomPathToApk(value, project);
    }
}