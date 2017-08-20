package com.dg.watcher.base;

import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.DrawingSupplier;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import static java.io.File.separator;
import static java.lang.System.getProperty;
import static org.jfree.chart.plot.DefaultDrawingSupplier.*;


public class Const {
    public static final String DB_FILE = separator + "apk_watcher" + separator + "database.csv";
    public static final String DB_ENCODING = "UTF-8";
    public static final String DB_ROW_SEPARATOR = getProperty("line.separator");
    public static final String DB_COLUMN_SEPARATOR = ",";

    public static final String APK_EXTENSION = "apk";
    public static final String APK_DEFAULT_DIR = "app" + separator + "build" + separator + "outputs" + separator + "apk";

    public static final boolean BUILD_ALLOWED = true;
    public static final boolean BUILD_FORBIDDEN = false;

    public static final int GRAPH_WIDTH  = 800;
    public static final int GRAPH_HEIGHT = 600;
    public static final int GRAPH_MAX_ENTRY_COUNT = 20;
    public static final String GRAPH_TITLE  = "Apk History";
    public static final String GRAPH_LEGEND = "Debug Apk";
    public static final String GRAPH_X_AXIS = "Build";
    public static final String GRAPH_Y_AXIS = "Megabyte";
    public static final String GRAPH_TOOLTIP = "Build %s: %.1f Megabyte";
    public static final BasicStroke GRAPH_LINE = new BasicStroke(1.5f);
    public static final DrawingSupplier GRAPH_LINE_DOT = new DefaultDrawingSupplier(
            DEFAULT_PAINT_SEQUENCE,
            DEFAULT_OUTLINE_PAINT_SEQUENCE,
            DEFAULT_STROKE_SEQUENCE,
            DEFAULT_OUTLINE_STROKE_SEQUENCE,
            new Shape[] { new Ellipse2D.Float(-4f, -4f, 8f, 8f) });

    public static final float CONVERSION_FACTOR_BYTE_TO_MEGABYTE  = 1024f * 1024f;
}