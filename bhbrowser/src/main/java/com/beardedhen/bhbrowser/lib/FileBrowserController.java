package com.beardedhen.bhbrowser.lib;

import android.text.TextUtils;
import android.util.Log;

import com.beardedhen.bhbrowser.views.FileSelectedListener;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileBrowserController implements FileBrowser, FileBrowserView.BrowserViewListener,
        Comparator<FolderItem>, Serializable {

    private final List<FolderItem> directoryContents = new ArrayList<>();
    private File currentPath = null;
    private FileSelectedListener listener;

    private final List<String> filterFileExtensions = new ArrayList<>();
    private SelectMode selectMode;
    private boolean showHiddenFiles;
    private DirectoryView view;
    private String browserTitle;

    public FileBrowserController(DirectoryView view, String startDir, FileSelectedListener listener) {
        this.view = view;
        this.view.setFileBrowserListener(this);
        this.listener = listener;

        if (TextUtils.isEmpty(startDir)) {
            startDir = "/";
        }

        this.selectMode = SelectMode.FILE;
        this.browserTitle = "Pick any file...";
        this.showHiddenFiles = true;

        changeDirectory(startDir);
    }

    @Override public void onUpClicked() {
        loadDirectoryUp();
        this.view.setDisplayedFolderItems(directoryContents);
    }

    @Override public void onNavigationItemClicked(FolderItem navigationItem) {
        String filename = navigationItem.getFileName();

        if (!TextUtils.isEmpty(filename)) {
            File sel = new File(currentPath, filename);
            changeDirectory(sel.getAbsolutePath());
        }
    }

    @Override public void setBrowserTitle(String title) {
        this.browserTitle = title;
        this.view.setBrowserTitle(title);
    }

    @Override public void loadDirectoryUp() {
        File parent = currentPath.getParentFile();
        changeDirectory(parent.getAbsolutePath());
    }

    @Override public void changeDirectory(String absPath) {
        if (!TextUtils.isEmpty(absPath)) {
            File file = new File(absPath);

            if (file.exists()) {
                String path = file.getAbsolutePath();

                if (file.isDirectory() && file.canRead()) {
                    this.currentPath = file;
                    loadFileList();
                    this.view.setCurrentDirectory(file.getAbsolutePath());
                    Log.i(Logger.TAG, "File browser navigated to path " + path);
                }
                else if (file.isFile() && file.canRead()) {
                    if (selectMode == SelectMode.FILE && listener != null) {
                        listener.fileSelected(file);
                    }
                }
                else {
//                    Toast.makeText(context, "Cannot read " + path, Toast.LENGTH_LONG).show();
                    Log.w(Logger.TAG, "File browser failed to navigate to path " + path);
                }

                boolean enableUpControl = file.getParentFile() != null;
                this.view.setUpControlEnabled(enableUpControl);
            }
        }
        loadFileList();
    }

    public void setFilterExtensions(List<String> extensions) {
        filterFileExtensions.clear();
        filterFileExtensions.addAll(extensions);
    }

    public void setFileSelectedListener(FileSelectedListener listener) {
        this.listener = listener;
    }

    public void setSelectMode(SelectMode selectMode) {
        this.selectMode = selectMode;
        this.view.setSelectControlEnabled(selectMode == SelectMode.DIR);
    }

    private void loadFileList() {
        directoryContents.clear();

        if (currentPath.exists() && currentPath.canRead()) {
            FilenameFilter filter = new FolderItemFilter(filterFileExtensions, showHiddenFiles);
            createFolderItemsForDirectory(filter);

            if (directoryContents.isEmpty()) {
                directoryContents.add(new FolderItem("Directory is empty", false, false, false));
            }
            else {
                Collections.sort(directoryContents, this);
            }
        }
        else {
            Log.e(Logger.TAG, "currentPath does not exist or cannot be read");
        }

        this.view.setDisplayedFolderItems(directoryContents);
    }

    private void createFolderItemsForDirectory(FilenameFilter filter) {
        for (String filename : currentPath.list(filter)) {
            File file = new File(currentPath, filename);

            boolean canRead = file.canRead();
            boolean isFile = file.isFile();
            boolean isHidden = file.isHidden();

            directoryContents.add(new FolderItem(filename, canRead, isFile, isHidden));
        }
    }

    @Override public void onSelectClicked() {
        if (selectMode == SelectMode.DIR && listener != null) {
            listener.fileSelected(currentPath);
        }
    }

    @Override public int compare(FolderItem lhs, FolderItem rhs) {
        return lhs.getFileName().toLowerCase().compareTo(rhs.getFileName().toLowerCase());
    }

    public void setShowHiddenFiles(boolean show) {
        showHiddenFiles = show;
    }

    @Override public void setDirectoryView(DirectoryView directoryView) {
        this.view = directoryView;
        this.view.setCurrentDirectory(currentPath.getAbsolutePath());
        this.view.setBrowserTitle(browserTitle);

        this.view.setDisplayedFolderItems(directoryContents);
        this.view.setSelectControlEnabled(selectMode == SelectMode.DIR);
        this.view.setUpControlEnabled(currentPath.getParentFile() != null);
        this.view.setFileBrowserListener(this);
    }
}
