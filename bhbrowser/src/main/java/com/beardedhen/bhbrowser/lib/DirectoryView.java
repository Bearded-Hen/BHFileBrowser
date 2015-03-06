package com.beardedhen.bhbrowser.lib;

import java.util.List;

public interface DirectoryView {

    /**
     * Update the view to display a list of folder items.
     *
     * @param folderItemList the folder items to be displayed
     */
    public void setDisplayedFolderItems(List<FolderItem> folderItemList);

    /**
     * Set displayed current directory (the absolute path)
     *
     * @param text text displayed to the user
     */
    public void setCurrentDirectory(String text);

    /**
     * Set displayed title for the browser (the current folder name)
     *
     * @param text text displayed to the user
     */
    public void setBrowserTitle(String text);

    /**
     * Whether the control to navigate up a directory should be enabled or not
     *
     * @param enabled true if enabled, otherwise false
     */
    public void setUpControlEnabled(boolean enabled);

    /**
     * Whether the control to select a file/folder should be enabled or not
     *
     * @param enabled true if enabled, otherwise false
     */
    public void setSelectControlEnabled(boolean enabled);

    /**
     * Registers for callbacks when the user selects controls in the view
     *
     * @param listener an implementation of the interface
     */
    public void setFileBrowserListener(FileBrowserView.BrowserViewListener listener);

}
