package com.dg.watcher.validation;

import hudson.FilePath;
import hudson.model.AbstractProject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.IOException;
import static com.dg.watcher.validation.InputValidation.validateCustomPathToApk;
import static com.dg.watcher.validation.InputValidation.validateThresholdInMb;
import static hudson.util.FormValidation.ok;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class InputValidationTest {
    @Rule
    public TemporaryFolder tempDir = new TemporaryFolder();


    @Before
    public void setUp() throws Exception {
        createTempApkFolder();
    }

    @Test
    public void should_allow_a_positive_threshold() {
        assertThat(validateThresholdInMb("2.5"), is(equalTo(ok())));
    }

    @Test
    public void should_indicate_a_negative_threshold_with_an_error_message() {
        assertThat(validateThresholdInMb("-2.5").getMessage(),
                is(equalTo("The threshold can not be negative.")));
    }

    @Test
    public void should_indicate_a_non_integral_threshold_with_an_error_message() {
        assertThat(validateThresholdInMb("abc").getMessage(),
                is(equalTo("The threshold must be a floating point number.")));
    }

    @Test
    public void should_allow_an_empty_custom_path_to_the_apk() {
        assertThat(validateCustomPathToApk("", mockProject()), is(equalTo(ok())));
    }

    @Test
    public void should_allow_an_existing_custom_path_to_the_apk() {
        assertThat(validateCustomPathToApk("temp_apk_folder", mockProject()), is(equalTo(ok())));
    }

    @Test
    public void should_indicate_a_invalid_custom_path_to_the_apk_with_an_error_message() {
        assertThat(validateCustomPathToApk("not/existing/path", mockProject()).getMessage(),
                is(equalTo("The provided path does not exist.")));
    }

    private void createTempApkFolder() throws IOException {
        tempDir.newFolder("temp_apk_folder");
    }

    private AbstractProject mockProject() {
        AbstractProject project = mock(AbstractProject.class);

        when(project.getSomeWorkspace()).thenReturn(new FilePath(tempDir.getRoot()));

        return project;
    }
}