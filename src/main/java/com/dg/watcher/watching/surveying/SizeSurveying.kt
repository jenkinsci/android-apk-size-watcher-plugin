package com.dg.watcher.watching.surveying

import com.dg.watcher.base.CONVERSION_FACTOR_BYTE_TO_MEGABYTE
import com.dg.watcher.watching.saving.SizeEntry
import com.dg.watcher.watching.surveying.SizeSurveyingResult.SIZE_THRESHOLD_EXCEEDED
import com.dg.watcher.watching.surveying.SizeSurveyingResult.SIZE_THRESHOLD_MET


fun surveySizes(sizes: List<SizeEntry>, thresholdInMb: Float): SizeSurveyingResult {
    if(atLeastTwoSizesRecorded(sizes)) {
        val latestSizeDifferenceInMb = calculateLatestSizeDifferenceInMb(sizes)

        if(sizeThresholdExceeded(latestSizeDifferenceInMb, thresholdInMb)) {
            return SIZE_THRESHOLD_EXCEEDED
        }
    }

    return SIZE_THRESHOLD_MET
}

private fun atLeastTwoSizesRecorded(sizes: List<SizeEntry>) = sizes.size > 1

private fun calculateLatestSizeDifferenceInMb(sizes: List<SizeEntry>): Float {
    val sizeOfPreviousApk = sizes[sizes.lastIndex - 1].sizeInByte
    val sizeOfLatestApk = sizes[sizes.lastIndex].sizeInByte

    return (sizeOfLatestApk - sizeOfPreviousApk) / CONVERSION_FACTOR_BYTE_TO_MEGABYTE
}

private fun sizeThresholdExceeded(differenceInMb: Float, thresholdInMb: Float) = differenceInMb > thresholdInMb