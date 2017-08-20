package com.dg.watcher.watching.saving;

import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.File;
import java.util.ArrayList;
import static com.dg.watcher.watching.saving.SizeSaving.loadApkSizes;
import static com.dg.watcher.watching.saving.SizeSaving.saveApkSize;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SizeSavingTest {
    @Rule
    public TemporaryFolder tempDir = new TemporaryFolder();


    @Test
    public void should_load_the_saved_size_entries() {
        // GIVEN
        saveApkSize(mockApkWithSizeInByte(10000000L), mockBuildWithName("#1"));
        saveApkSize(mockApkWithSizeInByte(11000000L), mockBuildWithName("#2"));

        // WHEN
        ArrayList<SizeEntry> loadedSizes = loadApkSizes(mockBuild());

        // THEN
        assertThat(loadedSizes, hasSize(2));
        assertThat(loadedSizes.get(0).getBuildName(),  is(equalTo("#1")));
        assertThat(loadedSizes.get(0).getSizeInByte(), is(equalTo(10000000L)));
        assertThat(loadedSizes.get(1).getBuildName(),  is(equalTo("#2")));
        assertThat(loadedSizes.get(1).getSizeInByte(), is(equalTo(11000000L)));
    }

    @Test
    public void should_not_load_any_size_entry_when_there_are_none_saved() {
        assertThat(loadApkSizes(mockBuild()), hasSize(0));
    }

    private AbstractBuild mockBuildWithName(String buildName) {
        AbstractBuild build = mockBuild();

        when(build.getDisplayName()).thenReturn(buildName);

        return build;
    }

    private AbstractBuild mockBuild() {
        AbstractProject project = mock(AbstractProject.class);
        when(project.getRootDir()).thenReturn(tempDir.getRoot());

        AbstractBuild build = mock(AbstractBuild.class);
        when(build.getProject()).thenReturn(project);

        return build;
    }

    private File mockApkWithSizeInByte(long sizeInByte) {
        File mock = mock(File.class);

        when(mock.length()).thenReturn(sizeInByte);

        return mock;
    }
}