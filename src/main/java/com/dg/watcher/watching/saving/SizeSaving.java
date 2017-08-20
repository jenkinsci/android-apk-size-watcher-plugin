package com.dg.watcher.watching.saving;

import hudson.model.AbstractBuild;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static com.dg.watcher.base.Const.*;
import static java.lang.Long.*;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.io.FileUtils.writeStringToFile;


public class SizeSaving {
    public static void saveApkSize(File apk, AbstractBuild<?, ?> build) {
        try {
            insertApkSize(apk, build);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<SizeEntry> loadApkSizes(AbstractBuild<?, ?> build) {
        ArrayList<SizeEntry> entries = new ArrayList<>();

        try {
            for(String row : loadSizeData(loadDatabaseFor(build))) {
                entries.add(loadSizeEntry(row));
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return entries;
    }

    private static void insertApkSize(File apk, AbstractBuild<?, ?> build) throws IOException {
        File db = loadDatabaseFor(build);

        writeStringToFile(db, createDatabaseRow(apk, build, db), DB_ENCODING, true);
    }

    private static File loadDatabaseFor(AbstractBuild<?, ?> build) {
        return new File(retrieveRootDir(build) + DB_FILE);
    }

    private static String retrieveRootDir(AbstractBuild<?, ?> build) {
        return build.getProject().getRootDir().getAbsolutePath();
    }

    private static String createDatabaseRow(File apk, AbstractBuild<?, ?> build, File db) {
        return createRowSeparator(db) + createRowData(apk, build);
    }

    private static String createRowSeparator(File database) {
        return database.exists() ? DB_ROW_SEPARATOR : "";
    }

    private static String createRowData(File apk, AbstractBuild<?, ?> build) {
        return build.getDisplayName() + DB_COLUMN_SEPARATOR + apk.length();
    }

    private static String[] loadSizeData(File database) throws IOException {
        if(database.exists()) {
            return readFileToString(database, DB_ENCODING).split(DB_ROW_SEPARATOR);
        }

        return new String[0];
    }

    private static SizeEntry loadSizeEntry(String entryRow) {
        String[] rowSplit = entryRow.split(DB_COLUMN_SEPARATOR);

        String name = rowSplit[0];
        String size = rowSplit[1];

        return new SizeEntry(name, valueOf(size));
    }
}