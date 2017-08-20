package com.dg.watcher.validation

import com.dg.watcher.validation.InputValidation.validateCustomPathToApk
import com.dg.watcher.validation.InputValidation.validateThresholdInMb
import com.nhaarman.mockito_kotlin.mock
import hudson.FilePath
import hudson.model.AbstractProject
import hudson.util.FormValidation
import hudson.util.FormValidation.ok
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.mockito.Mockito.`when`
import java.io.IOException


class InputValidationTest {
    @Rule @JvmField
    var tempDir = TemporaryFolder()


    @Before
    fun setUp() {
        createTempApkFolder()
    }

    @Test
    fun should_allow_a_positive_threshold() {
        assertThat<FormValidation>(validateThresholdInMb("2.5"), `is`<FormValidation>(equalTo<FormValidation>(ok())))
    }

    @Test
    fun should_indicate_a_negative_threshold_with_an_error_message() {
        assertThat<String>(validateThresholdInMb("-2.5").message,
                `is`(equalTo("The threshold can not be negative.")))
    }

    @Test
    fun should_indicate_a_non_integral_threshold_with_an_error_message() {
        assertThat<String>(validateThresholdInMb("abc").message,
                `is`(equalTo("The threshold must be a floating point number.")))
    }

    @Test
    fun should_allow_an_empty_custom_path_to_the_apk() {
        assertThat<FormValidation>(validateCustomPathToApk("", mockProject()), `is`<FormValidation>(equalTo<FormValidation>(ok())))
    }

    @Test
    fun should_allow_an_existing_custom_path_to_the_apk() {
        assertThat<FormValidation>(validateCustomPathToApk("temp_apk_folder", mockProject()), `is`<FormValidation>(equalTo<FormValidation>(ok())))
    }

    @Test
    fun should_indicate_a_invalid_custom_path_to_the_apk_with_an_error_message() {
        assertThat<String>(validateCustomPathToApk("not/existing/path", mockProject()).message,
                `is`(equalTo("The provided path does not exist.")))
    }

    @Throws(IOException::class)
    private fun createTempApkFolder() {
        tempDir.newFolder("temp_apk_folder")
    }

    private fun mockProject(): AbstractProject<*, *> {
        val project: AbstractProject<*, *> = mock()

        `when`<FilePath>(project.getSomeWorkspace()).thenReturn(FilePath(tempDir.root))

        return project
    }
}