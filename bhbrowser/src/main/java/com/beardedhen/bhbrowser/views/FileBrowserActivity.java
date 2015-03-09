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
import java.util.List;

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

        SelectMode selectMode = SelectMode.FILE;
        if (intent.hasExtra(Actions.FB_SELECT_MODE)) {
            selectMode = (SelectMode)
                    intent.getSerializableExtra(Actions.FB_SELECT_MODE);
        }

        List<String> extensionList = null;
        if (intent.hasExtra(Actions.FB_FILE_EXTENSIONS)) {
            extensionList = intent.getStringArrayListExtra(Actions.FB_FILE_EXTENSIONS);
        }

        boolean showHiddenFiles = false;
        if (intent.hasExtra(Actions.FB_SHOW_HIDDEN_FILES)) {
            showHiddenFiles = intent.getBooleanExtra(Actions.FB_SHOW_HIDDEN_FILES, true);
        }

        String browserTitle = null;
        if (intent.hasExtra(Actions.FB_BROWSER_TITLE)) {
            browserTitle = intent.getStringExtra(Actions.FB_BROWSER_TITLE);
            controller.setBrowserTitle(browserTitle);
        }

        FileBrowserController controller = new FileBrowserController(
                browserView,
                browserTitle,
                startDir,
                selectMode,
                showHiddenFiles,
                extensionList,
                this);

        return controller;
    }

    @Override public void fileSelected(File file) {
        Intent intent = new Intent();
        intent.putExtra(Actions.FB_RESULT_SELECTED_PATH, file.getAbsolutePath());
        setResult(RESULT_OK, intent);
        finish();
    }

}
