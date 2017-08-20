package com.dg.watcher;

import com.dg.watcher.history.History;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.BuildListener;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.IOException;
import java.io.PrintStream;
import static hudson.tasks.BuildStepMonitor.NONE;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;


public class PluginTest {
    @Rule
    public TemporaryFolder tempDir = new TemporaryFolder();


    @Before
    public void setUp() throws Exception {
        createApkFolder();
        createApkFile();
    }

    @Test
    public void should_not_require_a_monitor_service() {
        assertThat(plugin().getRequiredMonitorService(), is(equalTo(NONE)));
    }

    @Test
    public void should_provide_the_history_as_the_projects_action() {
        // WHEN
        Action action = plugin().getProjectAction(mockProject());

        // THEN
        assertThat(action, is(instanceOf(History.class)));
    }

    @Test
    public void should_update_the_history_action_when_a_build_is_performed() throws IOException, InterruptedException {
        // GIVEN
        History action = mockAction();

        // WHEN
        plugin().perform(mockBuildIncludesAction(action), mockLauncher(), mockListener());

        // THEN
        verify(action).updateHistory();
    }

    private void createApkFolder() throws IOException {
        tempDir.newFolder("temp_apk_folder");
    }

    private void createApkFile() throws IOException {
        tempDir.newFile("temp_apk_folder/debug.txt");
    }

    private Plugin plugin() {
        return new Plugin(0, "temp_apk_folder");
    }

    private AbstractProject mockProjectIncludesAction(History action) {
        AbstractProject project = mockProject();

        when(project.getAction(History.class)).thenReturn(action);

        return project;
    }

    private AbstractProject mockProject() {
        AbstractProject project = mock(AbstractProject.class);

        when(project.getRootDir()).thenReturn(tempDir.getRoot());

        return project;
    }

    private AbstractBuild mockBuildIncludesAction(History action) {
        AbstractProject project = mockProjectIncludesAction(action);
        AbstractBuild build = mockBuild();

        when(build.getProject()).thenReturn(project);

        return build;
    }

    private AbstractBuild mockBuild() {
        AbstractBuild build = mock(AbstractBuild.class);

        when(build.getWorkspace()).thenReturn(new FilePath(tempDir.getRoot()));

        return build;
    }

    private History mockAction() {
        return mock(History.class);
    }

    private Launcher mockLauncher() {
        return mock(Launcher.class);
    }

    private BuildListener mockListener() {
        BuildListener listener = mock(BuildListener.class);

        when(listener.getLogger()).thenReturn(mock(PrintStream.class));

        return listener;
    }
}