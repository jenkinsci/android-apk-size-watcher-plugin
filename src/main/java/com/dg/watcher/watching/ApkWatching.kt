package com.dg.watcher.watching

import com.dg.watcher.base.BUILD_ALLOWED
import com.dg.watcher.base.BUILD_FORBIDDEN
import com.dg.watcher.watching.retrieving.retrieveApk
import com.dg.watcher.watching.saving.loadApkSizes
import com.dg.watcher.watching.saving.saveApkSize
import com.dg.watcher.watching.surveying.SizeSurveyingResult.SIZE_THRESHOLD_EXCEEDED
import com.dg.watcher.watching.surveying.surveySizes
import hudson.model.AbstractBuild
import java.io.PrintStream


fun watchApkSize(build: AbstractBuild<*, *>, logger: PrintStream, thresholdInMb: Float, customPathToApk: String): Boolean {
    val apk = retrieveApk(build, customPathToApk)

    return if(apk != null) {
        saveApkSize(apk, build)

        evaluateSize(build, logger, thresholdInMb)
    }
    else {
        permitBuild(logger, thresholdInMb)
    }
}

private fun evaluateSize(build: AbstractBuild<*, *>, logger: PrintStream, thresholdInMb: Float) =
        when(surveySizes(loadApkSizes(build), thresholdInMb)) {
            SIZE_THRESHOLD_EXCEEDED -> cancelBuild(logger, thresholdInMb)

            else -> permitBuild(logger, thresholdInMb)
        }

private fun cancelBuild(logger: PrintStream, thresholdInMb: Float): Boolean {
    logger.println("Apk Watcher: Build Failed")
    logger.println("Apk Watcher: The size difference between the " +
            "last and the latest apk exceeded the specified threshold " +
            "of " + thresholdInMb + " megabyte.")

    return BUILD_FORBIDDEN
}

private fun permitBuild(logger: PrintStream, thresholdInMb: Float): Boolean {
    logger.println("Apk Watcher: Build Succeeded")
    logger.println("Apk Watcher: The size difference between the " +
            "last and the latest apk met the specified threshold " +
            "of " + thresholdInMb + " megabyte.")

    return BUILD_ALLOWED
}