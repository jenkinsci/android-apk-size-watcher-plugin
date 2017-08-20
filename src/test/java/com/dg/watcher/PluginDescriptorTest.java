package com.dg.watcher;

import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class PluginDescriptorTest {
    @Test
    public void should_specify_the_displayed_name_of_the_post_build_action() {
        assertThat(descriptor().getDisplayName(), is(equalTo("Watch your apk size")));
    }

    private PluginDescriptor descriptor() {
        return new PluginDescriptor();
    }
}