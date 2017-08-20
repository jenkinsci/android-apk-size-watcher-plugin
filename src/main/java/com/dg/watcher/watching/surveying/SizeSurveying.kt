package com.dg.watcher.watching.surveying

import com.dg.watcher.base.CONVERSION_FACTOR_BYTE_TO_MEGABYTE
import com.dg.watcher.watching.saving.SizeEntry
import java.util.ArrayList
import com.dg.watcher.watching.surveying.SizeSurveyingResult.SIZE_THRESHOLD_EXCEEDED
import com.dg.watcher.watching.surveying.SizeSurveyingResult.SIZE_THRESHOLD_MET


fun surveySizes(entries: ArrayList<SizeEntry>, thresholdInMb: Float): SizeSurveyingResult {
    if(atLeastTwoSizesRecorded(entries)) {
        val latestSizeDifferenceInMb = calculateLatestSizeDifferenceInMb(entries)

        if(sizeThresholdExceeded(latestSizeDifferenceInMb, thresholdInMb)) {
            return SIZE_THRESHOLD_EXCEEDED
        }
    }

    return SIZE_THRESHOLD_MET
}

private fun atLeastTwoSizesRecorded(entries: ArrayList<SizeEntry>) = entries.size > 1

private fun calculateLatestSizeDifferenceInMb(entries: ArrayList<SizeEntry>): Float {
    val previousApk = entries[entries.size - 2]
    val latestApk = entries[entries.size - 1]

    return (latestApk.sizeInByte - previousApk.sizeInByte) / CONVERSION_FACTOR_BYTE_TO_MEGABYTE
}

private fun sizeThresholdExceeded(differenceInMb: Float, thresholdInMb: Float) = differenceInMb > thresholdInMb