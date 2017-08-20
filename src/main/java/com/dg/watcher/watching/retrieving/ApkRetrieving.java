package com.dg.watcher.watching.retrieving;

import hudson.FilePath;
import hudson.model.AbstractBuild;
import java.io.File;
import java.util.ArrayList;
import static com.dg.watcher.base.Const.APK_DEFAULT_DIR;
import static com.dg.watcher.base.Const.APK_EXTENSION;
import static java.io.File.separator;
import static org.apache.commons.io.FileUtils.listFiles;


public class ApkRetrieving {
    public static File retrieveApk(AbstractBuild<?, ?> build, String customApkDir) {
        ArrayList<File> apkFiles = retrieveApkFiles(createPathToApk(build, customApkDir));

        return !apkFiles.isEmpty() ? apkFiles.get(0) : null;
    }

    private static String createPathToApk(AbstractBuild<?, ?> build, String customApkDir) {
        String workspaceDir = retrieveWorkspaceDir(build);

        if(customDirSupplied(customApkDir)) {
            return workspaceDir + customApkDir;
        }
        else {
            return workspaceDir + APK_DEFAULT_DIR;
        }
    }

    private static String retrieveWorkspaceDir(AbstractBuild<?, ?> build) {
        FilePath workspaceFile = build.getWorkspace();

        return workspaceFile != null ? workspaceFile.getRemote() + separator : "";
    }

    private static boolean customDirSupplied(String customApkDir) {
        return !customApkDir.isEmpty();
    }

    private static ArrayList<File> retrieveApkFiles(String apkDirPath) {
        File apkDirectory = new File(apkDirPath);

        if(apkDirectory.exists()) {
            return new ArrayList<>(listFiles(apkDirectory, new String[] { APK_EXTENSION }, true));
        }

        return new ArrayList<>();
    }
}