package com.dg.watcher.history.graph

import com.dg.watcher.base.CONVERSION_FACTOR_BYTE_TO_MEGABYTE
import com.dg.watcher.watching.saving.SizeEntry
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.equalTo
import org.jfree.data.category.DefaultCategoryDataset
import org.junit.Assert.assertThat
import org.junit.Test


class HistoryGraphDataSetGeneratorTest {
    @Test
    fun `Should include all 20 entries in the generated data set`() {
        // GIVEN
        val entries = arrayListOf<SizeEntry>()
        repeat(20) { entries.add(SizeEntry("#" + it, 0L)) }

        // WHEN
        val dataSet = generateGraphDataSet(entries)

        // THEN
        assertThat(retrieveEntries(dataSet), `is`(equalTo(entries)))
    }

    @Test
    fun `Should include the last 20 entries in the generated data set`() {
        // GIVEN
        val entries = arrayListOf<SizeEntry>()
        repeat(35) { entries.add(SizeEntry("#" + it, 0L)) }

        // WHEN
        val dataSet = generateGraphDataSet(entries)

        // THEN
        assertThat(retrieveEntries(dataSet), `is`(equalTo(entries.subList(15, 35))))
    }

    @Test
    fun `Should convert a entries size to megabyte for its row value`() {
        // GIVEN
        val entries = arrayListOf(SizeEntry("#1", sizeInByte = 10000000L))

        // WHEN
        val dataSet = generateGraphDataSet(entries)

        // THEN
        val rowValue = dataSet.getValue(0, 0).toFloat()

        assertThat(rowValue, `is`(10000000L / CONVERSION_FACTOR_BYTE_TO_MEGABYTE))
    }

    @Test
    fun `Should use a entries build name as its column key`() {
        // GIVEN
        val entries = arrayListOf(SizeEntry("#1", 0L))

        // WHEN
        val dataSet = generateGraphDataSet(entries)

        // THEN
        val columnKey = dataSet.getColumnKey(0).toString()

        assertThat(columnKey, `is`(equalTo("#1")))
    }

    private fun retrieveEntries(dataSet: DefaultCategoryDataset) = arrayListOf<SizeEntry>().apply {
        for(i in 0 until dataSet.columnCount) {
            val buildName = dataSet.getColumnKey(i).toString()

            val apkSizeInMb = dataSet.getValue(0, i).toFloat()
            val apkSizeInByte = (apkSizeInMb * CONVERSION_FACTOR_BYTE_TO_MEGABYTE).toLong()

            add(SizeEntry(buildName, apkSizeInByte))
        }
    }
}