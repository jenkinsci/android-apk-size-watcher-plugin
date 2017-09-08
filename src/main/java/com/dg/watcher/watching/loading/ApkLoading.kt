package com.dg.watcher.watching.loading

import com.dg.watcher.base.APK_DEFAULT_DIR
import com.dg.watcher.base.APK_FILE_FILTER
import com.dg.watcher.base.Build
import com.dg.watcher.base.extension.getFileInWorkspace
import hudson.FilePath


fun loadApk(build: Build, customPathToApk: String = ""): FilePath? {
    val apkDirectory = getApkDirectory(build, customPathToApk)

    return apkDirectory?.let { loadApkFiles(it).firstOrNull() }
}

private fun getApkDirectory(build: Build, customPathToApk: String) =
        build.getFileInWorkspace(decidePathToApk(customPathToApk))

private fun decidePathToApk(customPathToApk: String) =
    if(customPathToApk.isNotBlank()) {
        customPathToApk
    }
    else {
        APK_DEFAULT_DIR
    }

private fun loadApkFiles(apkDirectory: FilePath) =
    if(apkDirectory.exists()) {
        apkDirectory.list(APK_FILE_FILTER).toList()
    }
    else {
        emptyList()
    }