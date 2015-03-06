package com.beardedhen.bhbrowser.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.beardedhen.bhbrowser.R;

import java.util.List;

/**
 * A view that displays a List of FolderItems to the user
 */
public class FileBrowserView extends LinearLayout implements DirectoryView {

    public interface BrowserViewListener {
        /**
         * Callback for when the user clicks the Up navigation control
         */
        public void onUpClicked();

        public void onSelectClicked();

        /**
         * Callback for when the user selects an item to navigate to in the current dir
         *
         * @param navigationItem a folderitem
         */
        public void onNavigationItemClicked(FolderItem navigationItem);
    }

    private BrowserViewListener listener;
    private ListView listView;
    private TextView currentDirectory;
    private TextView browserTitle;
    private View backButton;
    private View selectControl;
    private View selectContainer;

    public FileBrowserView(Context context) {
        super(context);
        init();
    }

    public FileBrowserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FileBrowserView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.view_file_browser, this, false);

        this.addView(view);
        listView = (ListView) view.findViewById(R.id.browser_list_view);
        currentDirectory = (TextView) view.findViewById(R.id.current_directory);
        browserTitle = (TextView) view.findViewById(R.id.browser_title);

        backButton = view.findViewById(R.id.back_button);
        selectControl = view.findViewById(R.id.dir_select);
        selectContainer = view.findViewById(R.id.select_container);

        backButton.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                if (listener != null) {
                    listener.onUpClicked();
                }
            }
        });

        selectControl.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                if (listener != null) {
                    listener.onSelectClicked();
                }
            }
        });
    }

    @Override public void setDisplayedFolderItems(List<FolderItem> folderItemList) {
        final FolderItemAdapter adapter = new FolderItemAdapter(getContext(), folderItemList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    listener.onNavigationItemClicked(adapter.getItem(position));
                }
            }
        });
    }

    @Override public void setCurrentDirectory(String text) {
        currentDirectory.setText(text);
    }

    @Override public void setBrowserTitle(String text) {
        browserTitle.setText(text);
    }

    @Override public void setUpControlEnabled(boolean enabled) {
        backButton.setEnabled(enabled);
    }

    @Override public void setSelectControlEnabled(boolean enabled) {
        selectContainer.setVisibility(enabled ? VISIBLE : GONE);
    }

    @Override public void setFileBrowserListener(BrowserViewListener listener) {
        this.listener = listener;
    }

    private class FolderItemAdapter extends ArrayAdapter<FolderItem> {

        private LayoutInflater inflater;

        public FolderItemAdapter(Context context, List<FolderItem> values) {
            super(context, R.layout.list_item_folder_item, values);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_folder_item, parent, false);
            }

            FolderItem folderItem = getItem(position);

            TextView folderName = (TextView) convertView.findViewById(R.id.folder_item_name);
            FontAwesomeText faIcon = (FontAwesomeText) convertView.findViewById(R.id.folder_item_icon);

            folderName.setText(folderItem.getFileName());
            faIcon.setIcon(folderItem.getFaCode());
            faIcon.setTextColor(getResources().getColor(folderItem.getColor()));

            return convertView;
        }
    }

}
