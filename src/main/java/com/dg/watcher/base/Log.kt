package com.dg.watcher.base

import java.util.logging.Level
import java.util.logging.Level.INFO
import java.util.logging.Level.SEVERE
import java.util.logging.Level.WARNING
import java.util.logging.Logger.getLogger


fun err(tag: String, msg: String) = log(SEVERE, tag, msg)

fun warn(tag: String, msg: String) = log(WARNING, tag, msg)

fun info(tag: String, msg: String) = log(INFO, tag, msg)

private fun log(level: Level, tag: String, msg: String) = getLogger(tag).log(level, msg)