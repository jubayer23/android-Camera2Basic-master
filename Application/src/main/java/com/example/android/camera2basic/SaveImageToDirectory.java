package com.example.android.camera2basic;

import android.os.Environment;

import java.io.File;

public class SaveImageToDirectory {

    public static final String FOLDER_NAME = "retcamv";

    public static final String EXTENSION_JPEG = ".jpg";

    public static File getFileForSavingImage(String fileName, String extension) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/" + FOLDER_NAME + "/patientId_" + Camera2BaseFragment.patientId) ;

        if (!direct.exists()) {
            File wallpaperDirectory = new File("/sdcard/" + FOLDER_NAME + "/patientId_" + Camera2BaseFragment.patientId + "/");
            wallpaperDirectory.mkdirs();
        }

        File file = new File("/sdcard/" + FOLDER_NAME + "/patientId_" + Camera2BaseFragment.patientId + "/", fileName + extension);
        if (file.exists()) {
            file.delete();
        }

        return file;
    }
}
