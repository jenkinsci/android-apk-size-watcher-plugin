package com.dg.watcher.base.extension

import com.dg.watcher.base.Project


fun Project.getFileInWorkspace(path: String) = getSomeWorkspace()?.child(path)