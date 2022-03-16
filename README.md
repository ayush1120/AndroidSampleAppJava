# AndroidSampleAppJava


### Conclusion For StorageTest Activity for Android 11 SDK30+
- We can still get external storage directory path using without any additional permissions.

  ``` storageDirectoryPath = storageManager.getPrimaryStorageVolume().getDirectory().getAbsolutePath(); ```
- But on listing files, we only get contained directories and we do not get file in output of File.listFiles()
- To Solve this, we need to do the following as suggested on [Docs](https://developer.android.com/training/data-storage/manage-all-files "Docs") 
	1. Declare the MANAGE_EXTERNAL_STORAGE permission in the manifest.
	2. Use the ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION intent action to direct users to a system settings page where they can enable the following option for your app: Allow access to manage all files.
