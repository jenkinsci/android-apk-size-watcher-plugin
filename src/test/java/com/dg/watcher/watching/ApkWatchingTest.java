package com.dg.watcher.watching;

import hudson.FilePath;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import static com.dg.watcher.base.Const.BUILD_ALLOWED;
import static com.dg.watcher.base.Const.BUILD_FORBIDDEN;
import static org.apache.commons.io.FileUtils.cleanDirectory;
import static org.apache.commons.io.FileUtils.writeByteArrayToFile;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ApkWatchingTest {
    @Rule
    public TemporaryFolder tempDir = new TemporaryFolder();


    @Before
    public void setUp() throws Exception {
        createApkFolder();
    }

    @Test
    public void should_allow_the_initial_build_without_an_generated_apk() {
        // GIVEN
        AbstractBuild build = mockBuildWithoutAnApk();

        // WHEN
        boolean result = watchApkSize(build);

        // THEN
        assertThat(result, is(equalTo(BUILD_ALLOWED)));
    }

    @Test
    public void should_allow_the_initial_build_with_an_generated_apk() throws IOException {
        // GIVEN
        AbstractBuild build = mockBuildWithAnApkOfSizeInByte(10000000L);

        // WHEN
        boolean result = watchApkSize(build, 1f);

        // THEN
        assertThat(result, is(equalTo(BUILD_ALLOWED)));
    }

    @Test
    public void should_allow_the_build_when_the_generated_apk_meets_the_size_threshold() throws IOException {
        // GIVEN
        watchApkSize(mockBuildWithAnApkOfSizeInByte(10000000L));
        AbstractBuild build = mockBuildWithAnApkOfSizeInByte(11000000L);

        // WHEN
        boolean result = watchApkSize(build, 1f);

        // THEN
        assertThat(result, is(equalTo(BUILD_ALLOWED)));
    }

    @Test
    public void should_forbid_the_build_when_the_generated_apk_exceeds_the_size_threshold() throws IOException {
        // GIVEN
        watchApkSize(mockBuildWithAnApkOfSizeInByte(10000000L));
        AbstractBuild build = mockBuildWithAnApkOfSizeInByte(12000000L);

        // WHEN
        boolean result = watchApkSize(build, 1f);

        // THEN
        assertThat(result, is(equalTo(BUILD_FORBIDDEN)));
    }

    private boolean watchApkSize(AbstractBuild<?, ?> build, float thresholdInMb) {
        return ApkWatching.watchApkSize(build, mock(PrintStream.class), thresholdInMb, "");
    }

    private boolean watchApkSize(AbstractBuild<?, ?> build) {
        return watchApkSize(build, 0);
    }

    private AbstractBuild mockBuild() {
        AbstractProject project = mock(AbstractProject.class);
        when(project.getRootDir()).thenReturn(tempDir.getRoot());

        AbstractBuild build = mock(AbstractBuild.class);
        when(build.getProject()).thenReturn(project);
        when(build.getWorkspace()).thenReturn(new FilePath(tempDir.getRoot()));

        return build;
    }

    private AbstractBuild mockBuildWithoutAnApk() {
        return mockBuild();
    }

    private AbstractBuild mockBuildWithAnApkOfSizeInByte(long apkSizeInByte) throws IOException {
        createApkForBuild(apkSizeInByte);

        return mockBuild();
    }

    private void createApkFolder() throws IOException {
        tempDir.newFolder("app", "build", "outputs", "apk");
    }

    private void createApkForBuild(long apkSizeInByte) throws IOException {
        deleteOldApk();

        createNewApk(apkSizeInByte);
    }

    private void deleteOldApk() throws IOException {
        cleanDirectory(new File(tempDir.getRoot().getAbsolutePath()
                + "/app/build/outputs/apk"));
    }

    private void createNewApk(long apkSizeInByte) throws IOException {
        File apk = tempDir.newFile("app/build/outputs/apk/debug.apk");

        writeByteArrayToFile(apk, new byte[(int) apkSizeInByte]);
    }
}