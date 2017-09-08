package com.dg.watcher.base.extension

import hudson.FilePath


fun FilePath?.exists() = this?.exists() ?: false