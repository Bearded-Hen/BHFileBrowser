package com.beardedhen.bhbrowser.lib;

import android.support.annotation.ColorRes;

import com.beardedhen.bhbrowser.R;

import java.io.Serializable;

/**
 * A model that represents an item in a directory i.e. a File or a Folder
 */
public class FolderItem implements Serializable {

    private final String fileName;

    private boolean readable;

    private boolean isFile;

    private boolean isHidden;

    public FolderItem(String fileName, boolean readable, boolean isFile, boolean isHidden) {
        this.fileName = fileName;
        this.readable = readable;
        this.isFile = isFile;
        this.isHidden = isHidden;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isReadable() {
        return readable;
    }

    public boolean isFile() {
        return isFile;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public String getFaCode() {
        String faCode;

        if (!isReadable()) {
            faCode = "fa-question-circle";
        }
        else {
            if (isFile()) {
                faCode = "fa-file";
            }
            else {
                faCode = "fa-folder";
            }
        }
        return faCode;
    }

    @ColorRes public int getColor() {
        int colorRes;

        if (!isReadable()) {
            colorRes = R.color.text_default;
        }
        else {
            if (isFile()) {
                colorRes = R.color.file;
            }
            else {
                colorRes = R.color.folder;
            }
        }
        return colorRes;
    }

}
