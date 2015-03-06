package com.beardedhen.bhbrowser.lib;


import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class FolderItemFilter implements FilenameFilter {

    private final List<String> allowedExtensions = new ArrayList<>();
    private final boolean showHiddenFiles;

    public FolderItemFilter(List<String> allowedExtensions, boolean showHiddenFiles) {
        this.allowedExtensions.addAll(allowedExtensions);
        this.showHiddenFiles = showHiddenFiles;
    }

    public boolean accept(File dir, String filename) {

        boolean accept = true;
        File file = new File(dir, filename);

        if (file.isFile() && !matchesExtension(filename)) {
            accept = false;
        }

        if (file.isHidden() && !showHiddenFiles) {
            accept = false;
        }

        return accept;
    }

    private boolean matchesExtension(String filename) {
        boolean matches = allowedExtensions.isEmpty();

        if (!matches) {
            int extensionIndex = filename.lastIndexOf('.');

            if (extensionIndex != -1) {
                String fileExtension = filename.substring(extensionIndex, filename.length()).toLowerCase();

                for (String extension : allowedExtensions) {
                    if (fileExtension.toLowerCase().contains(extension)) {
                        matches = true;
                    }
                }
            }
        }
        return matches;
    }
}
