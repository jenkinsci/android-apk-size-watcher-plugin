package com.dg.watcher.base.extension

import com.dg.watcher.base.Build


fun Build.getFileInWorkspace(path: String) = getWorkspace()?.child(path)