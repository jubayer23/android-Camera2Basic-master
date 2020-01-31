package com.example.android.camera2basic;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SaveImageToDirectory {

    public static final String FOLDER_NAME = "retcamv";

    public static final String EXTENSION_JPEG = ".jpg";

    public static File getFileForSavingImage(String fileName, String extension) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/" + FOLDER_NAME + "/patientId_" + Camera2BaseFragment.patientId) ;

        if (!direct.exists()) {
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/" + FOLDER_NAME + "/patientId_" + Camera2BaseFragment.patientId);
            wallpaperDirectory.mkdirs();
        }


        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(direct.getPath() + File.separator
                + "IMG_" + timeStamp + EXTENSION_JPEG);


        //File file = new File("/sdcard/" + FOLDER_NAME + "/patientId_" + Camera2BaseFragment.patientId + "/", fileName + extension);
        if (mediaFile.exists()) {
            mediaFile.delete();
        }

        return mediaFile;
    }
}
