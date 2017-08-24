package com.dg.watcher.watching

import com.dg.watcher.base.BUILD_ALLOWED
import com.dg.watcher.base.BUILD_FORBIDDEN
import com.dg.watcher.watching.loading.loadApk
import com.dg.watcher.watching.saving.loadApkSizes
import com.dg.watcher.watching.saving.saveApkSize
import com.dg.watcher.watching.surveying.SizeSurveyingResult.SIZE_THRESHOLD_EXCEEDED
import com.dg.watcher.watching.surveying.surveySizes
import hudson.model.AbstractBuild
import java.io.PrintStream


fun watchApkSize(build: AbstractBuild<*, *>, logger: PrintStream, thresholdInMb: Float, customPathToApk: String = ""): Boolean {
    val apk = loadApk(build, customPathToApk)

    return if(apk != null) {
        saveApkSize(apk, build)

        evaluateSize(build, logger, thresholdInMb)
    }
    else {
        permitBuild(logger, thresholdInMb)
    }
}

private fun evaluateSize(build: AbstractBuild<*, *>, logger: PrintStream, thresholdInMb: Float) =
    if(surveySizes(loadApkSizes(build.getProject()), thresholdInMb) == SIZE_THRESHOLD_EXCEEDED) {
        cancelBuild(logger, thresholdInMb)
    }
    else {
        permitBuild(logger, thresholdInMb)
    }

private fun cancelBuild(logger: PrintStream, thresholdInMb: Float) = BUILD_FORBIDDEN.also {
    logger.println("Android Apk Size Watcher Plugin: Build Failed")
    logger.println("Android Apk Size Watcher Plugin: The size difference between your " +
            "last and latest .apk File exceeded the specified threshold of $thresholdInMb megabyte.")
}

private fun permitBuild(logger: PrintStream, thresholdInMb: Float) = BUILD_ALLOWED.also {
    logger.println("Android Apk Size Watcher Plugin: Build Succeeded")
    logger.println("Android Apk Size Watcher Plugin: The size difference between your " +
            "last and latest .apk File met the specified threshold of $thresholdInMb megabyte.")
}