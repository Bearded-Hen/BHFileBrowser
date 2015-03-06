package com.beardedhen.filebrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beardedhen.bhbrowser.lib.SelectMode;
import com.beardedhen.bhbrowser.views.Actions;
import com.beardedhen.bhbrowser.views.FileBrowserActivity;
import com.beardedhen.bhbrowser.views.FileBrowserDialog;
import com.beardedhen.bhbrowser.views.FileSelectedListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements FileSelectedListener {

    private static final int REQUEST_CODE = 9001;

    private FileBrowserDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button selectActivity = (Button) findViewById(R.id.select_activity);
        selectActivity.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                ArrayList<String> filterExtension = new ArrayList<>();
                filterExtension.add("apk");

                Intent intent = new Intent(MainActivity.this, FileBrowserActivity.class);

                intent.putExtra(Actions.FB_START_DIR, "/");
                intent.putExtra(Actions.FB_SHOW_HIDDEN_FILES, true);
                intent.putExtra(Actions.FB_SELECT_MODE, SelectMode.DIR);
                intent.putExtra(Actions.FB_FILE_EXTENSIONS, filterExtension);
                intent.putExtra(Actions.FB_BROWSER_TITLE, "Pick any file");

                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        Button selectDialog = (Button) findViewById(R.id.select_dialog);
        selectDialog.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ArrayList<String> filterExtension = new ArrayList<>();
                filterExtension.add("apk");

                Bundle bundle = new Bundle();
                bundle.putString(Actions.FB_START_DIR, "/");
                bundle.putBoolean(Actions.FB_SHOW_HIDDEN_FILES, true);
                bundle.putSerializable(Actions.FB_SELECT_MODE, SelectMode.FILE);
                bundle.putStringArrayList(Actions.FB_FILE_EXTENSIONS, filterExtension);
                bundle.putString(Actions.FB_BROWSER_TITLE, "Custom File Dialog");

                dialog = FileBrowserDialog.newInstance(bundle, MainActivity.this);
                dialog.show(getSupportFragmentManager(), null);
            }
        });
    }

    @Override public void fileSelected(File file) {
        Toast.makeText(this, "Got file " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();

        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String absPath = data.getStringExtra(Actions.FB_RESULT_SELECTED_PATH);
                Toast.makeText(this, "Got file " + absPath, Toast.LENGTH_LONG).show();
            }
        }
    }
}
