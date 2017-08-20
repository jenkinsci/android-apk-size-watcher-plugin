package com.dg.watcher.watching.surveying;

import com.dg.watcher.watching.saving.SizeEntry;
import java.util.ArrayList;
import static com.dg.watcher.base.Const.CONVERSION_FACTOR_BYTE_TO_MEGABYTE;
import static com.dg.watcher.watching.surveying.SizeSurveyingResult.SIZE_THRESHOLD_EXCEEDED;
import static com.dg.watcher.watching.surveying.SizeSurveyingResult.SIZE_THRESHOLD_MET;


public class SizeSurveying {
    public static SizeSurveyingResult surveySizes(ArrayList<SizeEntry> entries, float thresholdInMb) {
        if(atLeastTwoSizesRecorded(entries)) {
            float latestSizeDifferenceInMb = calculateLatestSizeDifferenceInMb(entries);

            if(sizeThresholdExceeded(latestSizeDifferenceInMb, thresholdInMb)) {
                return SIZE_THRESHOLD_EXCEEDED;
            }
        }

        return SIZE_THRESHOLD_MET;
    }

    private static boolean atLeastTwoSizesRecorded(ArrayList<SizeEntry> entries) {
        return entries.size() > 1;
    }

    private static float calculateLatestSizeDifferenceInMb(ArrayList<SizeEntry> entries) {
        SizeEntry previousApk = entries.get(entries.size() - 2);
        SizeEntry latestApk = entries.get(entries.size() - 1);

        return (latestApk.getSizeInByte() - previousApk.getSizeInByte())
                / CONVERSION_FACTOR_BYTE_TO_MEGABYTE;
    }

    private static boolean sizeThresholdExceeded(float differenceInMb, float thresholdInMb) {
        return differenceInMb > thresholdInMb;
    }
}