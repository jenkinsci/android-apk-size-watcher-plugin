package com.dg.watcher.watching.retrieving

import com.dg.watcher.base.APK_DEFAULT_DIR
import com.dg.watcher.base.APK_EXTENSION
import hudson.model.AbstractBuild
import org.apache.commons.io.FileUtils.listFiles
import java.io.File
import java.io.File.separator
import java.util.*


fun retrieveApk(build: AbstractBuild<*, *>, customApkDir: String): File? {
    val apkFiles = retrieveApkFiles(createPathToApk(build, customApkDir))

    return if(!apkFiles.isEmpty()) apkFiles.first() else null
}

private fun createPathToApk(build: AbstractBuild<*, *>, customApkDir: String): String {
    val workspaceDir = retrieveWorkspaceDir(build)

    return if(customApkDir.isNotEmpty()) {
        workspaceDir + customApkDir
    }
    else {
        workspaceDir + APK_DEFAULT_DIR
    }
}

private fun retrieveWorkspaceDir(build: AbstractBuild<*, *>): String {
    val workspaceFile = build.getWorkspace()

    return if(workspaceFile != null) "${workspaceFile.remote}$separator" else ""
}

private fun retrieveApkFiles(apkDirPath: String): ArrayList<File> {
    val apkDirectory = File(apkDirPath)

    return if(apkDirectory.exists()) {
        ArrayList(listFiles(apkDirectory, arrayOf(APK_EXTENSION), true))
    }
    else {
        ArrayList()
    }
}