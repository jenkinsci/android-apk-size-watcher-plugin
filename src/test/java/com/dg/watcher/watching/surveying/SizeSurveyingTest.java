package com.dg.watcher.watching.surveying;

import com.dg.watcher.watching.saving.SizeEntry;
import org.junit.Test;
import java.util.ArrayList;
import static com.dg.watcher.watching.surveying.SizeSurveying.surveySizes;
import static com.dg.watcher.watching.surveying.SizeSurveyingResult.SIZE_THRESHOLD_EXCEEDED;
import static com.dg.watcher.watching.surveying.SizeSurveyingResult.SIZE_THRESHOLD_MET;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


public class SizeSurveyingTest {
    @Test
    public void should_pass_the_size_survey_when_no_size_is_recorded() {
        // GIVEN
        ArrayList<SizeEntry> entries = new ArrayList<>();

        // THEN
        assertThat(surveySizes(entries, 1f), is(equalTo(SIZE_THRESHOLD_MET)));
    }

    @Test
    public void should_pass_the_size_survey_when_only_one_size_is_recorded() {
        // GIVEN
        ArrayList<SizeEntry> entries = new ArrayList<>();
        entries.add(new SizeEntry("#1", 10000000L));

        // THEN
        assertThat(surveySizes(entries, 1f), is(equalTo(SIZE_THRESHOLD_MET)));
    }

    @Test
    public void should_pass_the_size_survey_when_the_size_difference_is_below_the_threshold() {
        // GIVEN
        ArrayList<SizeEntry> entries = new ArrayList<>();
        entries.add(new SizeEntry("#1", 10000000L));
        entries.add(new SizeEntry("#2", 11000000L));

        // THEN
        assertThat(surveySizes(entries, 2f), is(equalTo(SIZE_THRESHOLD_MET)));
    }

    @Test
    public void should_pass_the_size_survey_when_the_size_difference_is_exactly_the_threshold() {
        // GIVEN
        ArrayList<SizeEntry> entries = new ArrayList<>();
        entries.add(new SizeEntry("#1", 10000000L));
        entries.add(new SizeEntry("#2", 12000000L));

        // THEN
        assertThat(surveySizes(entries, 2f), is(equalTo(SIZE_THRESHOLD_MET)));
    }

    @Test
    public void should_fail_the_size_survey_when_the_size_difference_exceeds_the_threshold() {
        // GIVEN
        ArrayList<SizeEntry> sizes = new ArrayList<>();
        sizes.add(new SizeEntry("#1", 10000000L));
        sizes.add(new SizeEntry("#2", 14000000L));

        // THEN
        assertThat(surveySizes(sizes, 2f), is(equalTo(SIZE_THRESHOLD_EXCEEDED)));
    }
}