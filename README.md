# BHFileBrowser

![alt text](https://github.com/Bearded-Hen/BHFileBrowser/blob/master/wiki/bhbrowser.png "Device Image")

### BHFileBrowser
BHFileBrowser is a simple file chooser for Android applications that uses FontAwesome icons. There are several configurable options, such as file extension filtering, displaying hidden files, and whether directories or files can be selected.

### Getting Started

Import the bhbrowser module into your Android Studio project and ensure that your app module specifies it as a dependency.

```java
dependencies {
   compile project (':bhbrowser')
}
```

### Using the library

You can either use a FileBrowserActivity:

```java
Intent intent = new Intent(MainActivity.this, FileBrowserActivity.class);
intent.putExtra(Actions.FB_START_DIR, "/");
intent.putExtra(Actions.FB_SHOW_HIDDEN_FILES, true);
intent.putExtra(Actions.FB_SELECT_MODE, SelectMode.DIR);
intent.putExtra(Actions.FB_FILE_EXTENSIONS, filterExtension);
intent.putExtra(Actions.FB_BROWSER_TITLE, "Pick any file");
startActivityForResult(intent, REQUEST_CODE);
```

Or a FileBrowserDialog:

```java
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
```

### Issues/Contributing
If you discover a bug/enhancement or just have a question, please create a ticket in the issue tracker. Be sure to include reproduction steps and if relevant Android version/model.
