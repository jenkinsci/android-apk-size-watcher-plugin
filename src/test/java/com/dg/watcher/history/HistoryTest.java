package com.dg.watcher.history;

import hudson.model.AbstractProject;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;


public class HistoryTest {
    @Test
    public void should_specify_the_shown_icon() {
        assertThat(history().getIconFileName(), is(equalTo("graph.gif")));
    }

    @Test
    public void should_specify_the_shown_label() {
        assertThat(history().getDisplayName(), is(equalTo("Apk History")));
    }

    @Test
    public void should_specify_the_used_domain() {
        assertThat(history().getUrlName(), is(equalTo("apk_history")));
    }

    private History history() {
        return new History(mock(AbstractProject.class));
    }
}