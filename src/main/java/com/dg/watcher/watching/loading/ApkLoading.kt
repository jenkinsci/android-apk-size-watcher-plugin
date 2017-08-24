package com.dg.watcher.watching.loading

import com.dg.watcher.base.APK_DEFAULT_DIR
import com.dg.watcher.base.APK_EXTENSION
import com.dg.watcher.base.Build
import org.apache.commons.io.FileUtils.listFiles
import java.io.File
import java.io.File.separator


fun loadApk(build: Build, customApkDir: String = "") =
        loadApkFiles(getPathToApkDir(build, customApkDir)).firstOrNull()

private fun getPathToApkDir(build: Build, customApkDir: String): String {
    val workspaceDir = getWorkspaceDir(build)

    return if(customApkDir.isNotBlank()) {
        workspaceDir + customApkDir
    }
    else {
        workspaceDir + APK_DEFAULT_DIR
    }
}

private fun getWorkspaceDir(build: Build) = build.getWorkspace()?.remote + separator

private fun loadApkFiles(pathToApkDir: String): List<File> {
    val apkDirectory = File(pathToApkDir)

    return if(apkDirectory.exists()) {
        listFiles(apkDirectory, arrayOf(APK_EXTENSION), true).toList()
    }
    else {
        emptyList()
    }
}