package com.dg.watcher.watching.saving;

import com.dg.watcher.watching.saving.SizeEntry;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


public class SizeEntryTest {
    @Test
    public void should_compare_two_equal_size_entries() {
        // GIVEN
        SizeEntry entryA = new SizeEntry("#1", 10000000L);
        SizeEntry entryB = new SizeEntry("#1", 10000000L);

        // THEN
        assertThat(entryA, is(equalTo(entryB)));
    }

    @Test
    public void should_convert_a_size_entry_to_string() {
        // GIVEN
        SizeEntry entry = new SizeEntry("#1", 10000000L);

        // THEN
        assertThat(entry, hasToString("Name: #1 Size: 10000000 Byte"));
    }
}