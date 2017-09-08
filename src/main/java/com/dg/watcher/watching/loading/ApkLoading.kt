package com.dg.watcher.watching.loading

import com.dg.watcher.base.APK_DEFAULT_DIR
import com.dg.watcher.base.APK_EXTENSION
import com.dg.watcher.base.Build
import hudson.FilePath


fun loadApk(build: Build, customApkDir: String = "") =
        loadApkFiles(getPathToApkDir(build, customApkDir)).firstOrNull()

private fun getPathToApkDir(build: Build, customApkDir: String): FilePath? {
    val workspaceDir = getWorkspaceDir(build)

    return if(customApkDir.isNotBlank()) {
        workspaceDir?.child(customApkDir)
    }
    else {
        workspaceDir?.child(APK_DEFAULT_DIR)
    }
}

private fun getWorkspaceDir(build: Build) = build.getWorkspace()

private fun loadApkFiles(pathToApkDir: FilePath?): List<FilePath> {

    return if(pathToApkDir?.exists() == true) {
        pathToApkDir.list("*.$APK_EXTENSION").toList()
    }
    else {
        emptyList()
    }
}