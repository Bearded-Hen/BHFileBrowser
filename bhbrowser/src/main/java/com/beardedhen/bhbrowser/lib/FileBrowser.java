package com.beardedhen.bhbrowser.lib;

/**
 * Provides methods for navigating the file system in Android
 */
public interface FileBrowser {

    /**
     * Change directory to a folder with the name specified
     *
     * @param absPath the absolute path
     */
    public void changeDirectory(String absPath);

    /**
     * Go up one directory. If already at root, do nothing.
     */
    public void loadDirectoryUp();

    public void setDirectoryView(DirectoryView directoryView);

    public void setBrowserTitle(String title);

}
