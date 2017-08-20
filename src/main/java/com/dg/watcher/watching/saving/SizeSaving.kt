package com.dg.watcher.watching.saving

import com.dg.watcher.base.DB_COLUMN_SEPARATOR
import hudson.model.AbstractBuild
import java.io.File
import java.io.IOException
import com.dg.watcher.base.DB_ENCODING
import com.dg.watcher.base.DB_FILE
import com.dg.watcher.base.DB_ROW_SEPARATOR
import java.lang.Long.*
import org.apache.commons.io.FileUtils.readFileToString
import org.apache.commons.io.FileUtils.writeStringToFile


fun saveApkSize(apk: File, build: AbstractBuild<*, *>) {
    try {
        insertApkSize(apk, build)
    }
    catch(e: IOException) {
        e.printStackTrace()
    }
}

fun loadApkSizes(build: AbstractBuild<*, *>) = arrayListOf<SizeEntry>().apply {
    try {
        retrieveRowsFromDatabase(loadDatabaseFor(build)).forEach {
            add(createSizeEntryFromRow(it))
        }
    }
    catch(e: IOException) {
        e.printStackTrace()
    }
}

private fun insertApkSize(apk: File, build: AbstractBuild<*, *>) {
    val db = loadDatabaseFor(build)

    writeStringToFile(db, createDatabaseRow(apk, build, db), DB_ENCODING, true)
}

private fun loadDatabaseFor(build: AbstractBuild<*, *>) = File(retrieveRootDir(build) + DB_FILE)

private fun retrieveRootDir(build: AbstractBuild<*, *>) = build.getProject().getRootDir().absolutePath

private fun createDatabaseRow(apk: File, build: AbstractBuild<*, *>, db: File) = createRowSeparator(db) + createRowData(apk, build)

private fun createRowSeparator(database: File) = if(database.exists()) DB_ROW_SEPARATOR else ""

private fun createRowData(apk: File, build: AbstractBuild<*, *>) = "${build.getDisplayName()}$DB_COLUMN_SEPARATOR${apk.length()}"

private fun retrieveRowsFromDatabase(database: File) = when {
    database.exists() -> readFileToString(database, DB_ENCODING).split(DB_ROW_SEPARATOR)

    else -> emptyList()
}

private fun createSizeEntryFromRow(entryRow: String): SizeEntry {
    val rowSplit = entryRow.split(DB_COLUMN_SEPARATOR)

    val name = rowSplit.first()
    val size = rowSplit.last()

    return SizeEntry(name, valueOf(size))
}