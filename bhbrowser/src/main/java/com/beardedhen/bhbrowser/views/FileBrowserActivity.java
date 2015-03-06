package com.beardedhen.bhbrowser.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.beardedhen.bhbrowser.R;
import com.beardedhen.bhbrowser.lib.DirectoryView;
import com.beardedhen.bhbrowser.lib.FileBrowserController;
import com.beardedhen.bhbrowser.lib.SelectMode;

import java.io.File;
import java.util.ArrayList;

public class FileBrowserActivity extends Activity implements FileSelectedListener {

    private DirectoryView browserView;
    private FileBrowserController controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_picker);
        browserView = (DirectoryView) findViewById(R.id.file_browser);

        if (savedInstanceState == null) {
            controller = buildBrowserInstance(getIntent());
        }
        else {
            controller = (FileBrowserController) savedInstanceState.getSerializable(Actions.KEY_CONTROLLER);
            controller.setDirectoryView(browserView);
        }
    }

    private FileBrowserController buildBrowserInstance(Intent intent) {
        String startDir = null;

        if (intent.hasExtra(Actions.FB_START_DIR)) {
            startDir = intent.getStringExtra(Actions.FB_START_DIR);
        }

        FileBrowserController controller = new FileBrowserController(browserView, startDir, this);

        if (intent.hasExtra(Actions.FB_SELECT_MODE)) {
            SelectMode selectMode = (SelectMode)
                    intent.getSerializableExtra(Actions.FB_SELECT_MODE);
            controller.setSelectMode(selectMode);
        }

        if (intent.hasExtra(Actions.FB_FILE_EXTENSIONS)) {
            ArrayList<String> extensionList = intent.getStringArrayListExtra(Actions.FB_FILE_EXTENSIONS);
            controller.setFilterExtensions(extensionList);
        }
        if (intent.hasExtra(Actions.FB_SHOW_HIDDEN_FILES)) {
            boolean showHiddenFiles = intent.getBooleanExtra(Actions.FB_SHOW_HIDDEN_FILES, true);
            controller.setShowHiddenFiles(showHiddenFiles);
        }
        if (intent.hasExtra(Actions.FB_BROWSER_TITLE)) {
            String browserTitle = intent.getStringExtra(Actions.FB_BROWSER_TITLE);
            controller.setBrowserTitle(browserTitle);
        }
        return controller;
    }

    @Override public void fileSelected(File file) {
        Intent intent = new Intent();
        intent.putExtra(Actions.FB_RESULT_SELECTED_PATH, file.getAbsolutePath());
        setResult(RESULT_OK, intent);
        finish();
    }

}
