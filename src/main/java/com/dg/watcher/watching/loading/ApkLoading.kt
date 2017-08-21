package com.dg.watcher.watching.loading

import com.dg.watcher.base.APK_DEFAULT_DIR
import com.dg.watcher.base.APK_EXTENSION
import hudson.model.AbstractBuild
import org.apache.commons.io.FileUtils.listFiles
import java.io.File
import java.io.File.separator


fun loadApk(build: AbstractBuild<*, *>, customApkDir: String = "") =
        loadApkFiles(getPathToApkDir(build, customApkDir)).firstOrNull()

private fun getPathToApkDir(build: AbstractBuild<*, *>, customApkDir: String): String {
    val workspaceDir = getWorkspaceDir(build)

    return if(customApkDir.isNotBlank()) {
        workspaceDir + customApkDir
    }
    else {
        workspaceDir + APK_DEFAULT_DIR
    }
}

private fun getWorkspaceDir(build: AbstractBuild<*, *>): String {
    val workspace = build.getWorkspace()

    return if(workspace != null) workspace.remote + separator else ""
}

private fun loadApkFiles(pathToApkDir: String): List<File> {
    val apkDirectory = File(pathToApkDir)

    return if(apkDirectory.exists()) {
        listFiles(apkDirectory, arrayOf(APK_EXTENSION), true).toList()
    }
    else {
        emptyList()
    }
}