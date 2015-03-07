package com.beardedhen.bhbrowser.views;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.beardedhen.bhbrowser.R;
import com.beardedhen.bhbrowser.lib.DirectoryView;
import com.beardedhen.bhbrowser.lib.FileBrowserController;
import com.beardedhen.bhbrowser.lib.SelectMode;

import java.io.File;
import java.util.ArrayList;

public class FileBrowserDialog extends DialogFragment {

    private FileSelectedListener listener;
    private FileBrowserController controller;
    private DirectoryView directoryView;

    public static FileBrowserDialog newInstance(final Bundle args,
                                                final FileSelectedListener listener) {
        final FileBrowserDialog fragment = new FileBrowserDialog();
        fragment.setArguments(args);
        fragment.setListener( new FileSelectedListener() {
            @Override
            public void fileSelected(File file) {
                listener.fileSelected( file );
                fragment.dismiss();
            }
        });
        return fragment;
    }

    @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.file_picker, container, false);
        directoryView = (DirectoryView) view;

        if (savedInstanceState == null) {
            controller = buildBrowserInstance(getArguments());
        }
        else {
            controller = (FileBrowserController) savedInstanceState.getSerializable(Actions.KEY_CONTROLLER);
            controller.setDirectoryView(directoryView);
        }

        return view;
    }

    private FileBrowserController buildBrowserInstance(Bundle args) {
        String startDir = args.getString(Actions.FB_START_DIR);
        String browserTitle = args.getString(Actions.FB_BROWSER_TITLE);
        boolean showHiddenFiles = args.getBoolean(Actions.FB_SHOW_HIDDEN_FILES, true);

        ArrayList<String> extensionList = args.getStringArrayList(Actions.FB_FILE_EXTENSIONS);
        SelectMode selectMode = (SelectMode)
                args.getSerializable(Actions.FB_SELECT_MODE);

        FileBrowserController controller = new FileBrowserController(directoryView, startDir, listener);
        controller.setSelectMode(selectMode != null ? selectMode : SelectMode.FILE);
        controller.setShowHiddenFiles(showHiddenFiles);

        if (extensionList != null) {
            controller.setFilterExtensions(extensionList);
        }
        if (browserTitle != null) {
            controller.setBrowserTitle(browserTitle);
        }
        return controller;
    }

    private void setListener(FileSelectedListener listener) {
        this.listener = listener;
    }

}
