package com.dg.watcher.watching.saving

import com.dg.watcher.base.DB_COLUMN_SEPARATOR
import com.dg.watcher.base.DB_ENCODING
import com.dg.watcher.base.DB_FILE
import com.dg.watcher.base.DB_ROW_SEPARATOR
import hudson.model.AbstractBuild
import hudson.model.AbstractProject
import org.apache.commons.io.FileUtils.readFileToString
import org.apache.commons.io.FileUtils.writeStringToFile
import java.io.File
import java.io.IOException


fun saveApkSize(apk: File, build: AbstractBuild<*, *>) {
    try {
        insertApkSize(apk, build)
    }
    catch(e: IOException) {
        e.printStackTrace()
    }
}

fun loadApkSizes(project: AbstractProject<*, *>) =
    try {
        val sizes = arrayListOf<SizeEntry>()

        loadRowsFromDatabase(loadDatabase(project)).forEach {
            sizes += createSizeEntryFromRow(it)
        }

        sizes.toList()
    }
    catch(e: IOException) {
        e.printStackTrace()

        emptyList<SizeEntry>()
    }

private fun insertApkSize(apk: File, build: AbstractBuild<*, *>) {
    val db = loadDatabase(build.getProject())

    writeStringToFile(db, createDatabaseRow(apk, build, db), DB_ENCODING, true)
}

private fun loadDatabase(project: AbstractProject<*, *>) = File(project.getRootDir().absolutePath + DB_FILE)

private fun createDatabaseRow(apk: File, build: AbstractBuild<*, *>, db: File) = createRowSeparator(db) + createRowData(apk, build)

private fun createRowSeparator(database: File) = if(database.exists()) DB_ROW_SEPARATOR else ""

private fun createRowData(apk: File, build: AbstractBuild<*, *>) = build.getDisplayName() + DB_COLUMN_SEPARATOR + apk.length()

private fun loadRowsFromDatabase(database: File) =
    if(database.exists()) {
        readFileToString(database, DB_ENCODING).split(DB_ROW_SEPARATOR)
    }
    else {
        emptyList()
    }

private fun createSizeEntryFromRow(entryRow: String): SizeEntry {
    val (name, size) = entryRow.split(DB_COLUMN_SEPARATOR)

    return SizeEntry(name, size.toLong())
}