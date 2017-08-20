package com.dg.watcher.base

import org.jfree.chart.plot.DefaultDrawingSupplier
import org.jfree.chart.plot.DefaultDrawingSupplier.*
import java.awt.BasicStroke
import java.awt.geom.Ellipse2D
import java.io.File.separator
import java.lang.System.getProperty


val DB_FILE = "${separator}apk_size_watcher${separator}database.csv"
val DB_ENCODING = "UTF-8"
val DB_ROW_SEPARATOR: String = getProperty("line.separator")
val DB_COLUMN_SEPARATOR = ","

val APK_EXTENSION = "apk"
val APK_DEFAULT_DIR = "app${separator}build${separator}outputs${separator}apk"

val BUILD_ALLOWED = true
val BUILD_FORBIDDEN = false

val GRAPH_WIDTH = 800
val GRAPH_HEIGHT = 600
val GRAPH_MAX_ENTRY_COUNT = 20
val GRAPH_TITLE = "Apk Size History"
val GRAPH_LEGEND = "Debug Apk"
val GRAPH_X_AXIS = "Build"
val GRAPH_Y_AXIS = "Megabyte"
val GRAPH_TOOLTIP = "Build %s: %.1f Megabyte"
val GRAPH_LINE = BasicStroke(1.5f)
val GRAPH_LINE_DOT = DefaultDrawingSupplier(
        DEFAULT_PAINT_SEQUENCE,
        DEFAULT_OUTLINE_PAINT_SEQUENCE,
        DEFAULT_STROKE_SEQUENCE,
        DEFAULT_OUTLINE_STROKE_SEQUENCE,
        arrayOf(Ellipse2D.Float(-4f, -4f, 8f, 8f)))

val CONVERSION_FACTOR_BYTE_TO_MEGABYTE = 1024f * 1024f