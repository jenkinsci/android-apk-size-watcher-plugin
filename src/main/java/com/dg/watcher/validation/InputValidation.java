package com.dg.watcher.validation;

import hudson.model.AbstractProject;
import hudson.util.FormValidation;
import java.io.File;
import static hudson.util.FormValidation.error;
import static hudson.util.FormValidation.ok;
import static java.io.File.separator;
import static java.lang.Float.*;


public class InputValidation {
    public static FormValidation validateThresholdInMb(String input) {
        try {
            float thresholdInMb = parseFloat(input);

            if(thresholdInMb >= 0f) {
                return ok();
            }
            else {
                return error("The threshold can not be negative.");
            }
        }
        catch(Exception e) {
            return error("The threshold must be a floating point number.");
        }
    }

    public static FormValidation validateCustomPathToApk(String input, AbstractProject<?, ?> project) {
        if(noPathSpecified(input) || validPathSpecified(input, project)) {
            return ok();
        }
        else {
            return error("The provided path does not exist.");
        }
    }

    private static boolean noPathSpecified(String input) {
        return input.isEmpty();
    }

    private static boolean validPathSpecified(String input, AbstractProject<?, ?> project) {
        return new File(retrieveWorkSpaceRoot(project) + input).exists();
    }

    private static String retrieveWorkSpaceRoot(AbstractProject<?, ?> project) {
        return project.getSomeWorkspace() + separator;
    }
}