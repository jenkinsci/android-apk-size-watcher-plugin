package com.dg.watcher

import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Test


class PluginDescriptorTest {
    @Test
    fun `Should specify the name of the post build action`() = assertThat(PluginDescriptor().displayName,
            Matchers.`is`(Matchers.equalTo("Watch over the changing size of your .apk file")))
}