package com.dg.watcher.watching;

import hudson.model.AbstractBuild;
import java.io.File;
import java.io.PrintStream;
import static com.dg.watcher.watching.retrieving.ApkRetrieving.retrieveApk;
import static com.dg.watcher.base.Const.BUILD_FORBIDDEN;
import static com.dg.watcher.base.Const.BUILD_ALLOWED;
import static com.dg.watcher.watching.saving.SizeSaving.loadApkSizes;
import static com.dg.watcher.watching.saving.SizeSaving.saveApkSize;
import static com.dg.watcher.watching.surveying.SizeSurveying.surveySizes;


public class ApkWatching {
    public static boolean watchApkSize(AbstractBuild<?, ?> build, PrintStream logger, float thresholdInMb, String customPathToApk) {
        File apk = retrieveApk(build, customPathToApk);

        if(apkFound(apk)) {
            saveApkSize(apk, build);

            return evaluateSize(build, logger, thresholdInMb);
        }
        else {
            return permitBuild(logger, thresholdInMb);
        }
    }

    private static boolean evaluateSize(AbstractBuild<?, ?> build, PrintStream logger, float thresholdInMb) {
        switch(surveySizes(loadApkSizes(build), thresholdInMb)) {
            case SIZE_THRESHOLD_EXCEEDED:
                return cancelBuild(logger, thresholdInMb);
            default:
                return permitBuild(logger, thresholdInMb);
        }
    }

    private static boolean apkFound(File apk) {
        return apk != null;
    }

    private static boolean cancelBuild(PrintStream logger, float thresholdInMb) {
        logger.println("Apk Watcher: Build Failed");
        logger.println("Apk Watcher: The size difference between the " +
                "last and the latest apk exceeded the specified threshold " +
                "of " + thresholdInMb + " megabyte.");

        return BUILD_FORBIDDEN;
    }

    private static boolean permitBuild(PrintStream logger, float thresholdInMb) {
        logger.println("Apk Watcher: Build Succeeded");
        logger.println("Apk Watcher: The size difference between the " +
                "last and the latest apk met the specified threshold " +
                "of " + thresholdInMb + " megabyte.");

        return BUILD_ALLOWED;
    }
}