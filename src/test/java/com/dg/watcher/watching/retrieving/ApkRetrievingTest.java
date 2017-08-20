package com.dg.watcher.watching.retrieving;

import hudson.FilePath;
import hudson.model.AbstractBuild;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.IOException;
import static com.dg.watcher.watching.retrieving.ApkRetrieving.retrieveApk;
import static java.io.File.separator;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ApkRetrievingTest {
    @Rule
    public TemporaryFolder tempDir = new TemporaryFolder();


    @Test
    public void should_return_null_when_the_specified_folder_is_non_existent() throws IOException {
        assertNull(retrieveApk(mockBuild(), "temp_apk_folder"));
    }

    @Test
    public void should_return_null_when_the_apk_in_the_specified_folder_is_non_existent() throws IOException {
        // GIVEN
        createApkFolder("temp_apk_folder");

        // THEN
        assertNull(retrieveApk(mockBuild(), "temp_apk_folder"));
    }

    @Test
    public void should_retrieve_the_apk_from_the_specified_folder() throws IOException {
        // GIVEN
        createApkFolder("temp_apk_folder");
        createApkFile("temp_apk_folder", "debug.apk");

        // THEN
        assertNotNull(retrieveApk(mockBuild(), "temp_apk_folder"));
    }

    @Test
    public void should_return_null_when_the_default_folder_is_non_existent() throws IOException {
        assertNull(retrieveApk(mockBuild(), ""));
    }

    @Test
    public void should_return_null_when_the_apk_in_the_default_folder_is_non_existent() throws IOException {
        // GIVEN
        createApkFolder("app", "build", "outputs", "apk");

        // THEN
        assertNull(retrieveApk(mockBuild(), ""));
    }

    @Test
    public void should_retrieve_the_apk_from_the_default_folder() throws IOException {
        // GIVEN
        createApkFolder("app", "build", "outputs", "apk");
        createApkFile("app/build/outputs/apk",  "debug.apk");

        // THEN
        assertNotNull(retrieveApk(mockBuild(), ""));
    }

    private void createApkFolder(String... folders) throws IOException {
        tempDir.newFolder(folders);
    }

    private void createApkFile(String folder, String fileName) throws IOException {
        tempDir.newFile(folder + separator + fileName);
    }

    private AbstractBuild mockBuild() {
        AbstractBuild build = mock(AbstractBuild.class);

        when(build.getWorkspace()).thenReturn(new FilePath(tempDir.getRoot()));

        return build;
    }
}