package com.dg.watcher.validation

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import hudson.FilePath
import hudson.model.AbstractProject
import hudson.util.FormValidation.ok
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder


class InputValidationTest {
    @Rule @JvmField
    var tempDir = TemporaryFolder()


    @Before
    fun setUp() {
        createTempApkFolder()
    }

    @Test
    fun `Should allow a positive threshold`() =
            assertThat(validateThresholdInMb("2.5"), `is`(equalTo(ok())))

    @Test
    fun `Should indicate a negative threshold with an error message`() =
            assertThat(validateThresholdInMb("-2.5").message, `is`(equalTo("The threshold cannot be negative.")))

    @Test
    fun `Should indicate a non integral threshold with an error message`() =
            assertThat(validateThresholdInMb("abc").message, `is`(equalTo("The threshold must be a floating point number.")))

    @Test
    fun `Should allow an empty custom path to the apk`() =
            assertThat(validateCustomPathToApk("", mockProject()), `is`(equalTo(ok())))

    @Test
    fun `Should allow an existing custom path to the apk`() =
            assertThat(validateCustomPathToApk("temp_apk_folder", mockProject()), `is`(equalTo(ok())))

    @Test
    fun `Should indicate a invalid custom path to the apk with an error message`() =
            assertThat(validateCustomPathToApk("not/existing/path", mockProject()).message,
                    `is`(equalTo("The specified path does not exist.")))

    private fun createTempApkFolder() = tempDir.newFolder("temp_apk_folder")

    private fun mockProject() = mock<AbstractProject<*, *>> {
        on { getSomeWorkspace() } doReturn FilePath(tempDir.root)
    }
}