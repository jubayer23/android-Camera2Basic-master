package com.example.android.camera2basic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.RelativeLayout;

import com.crashlytics.android.Crashlytics;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static com.example.android.camera2basic.CameraVideoFragment.VIDEO_DIRECTORY_NAME;

public class CameraActivity2 extends AppCompatActivity {

    RelativeLayout rl_pathhole;

    Fragment photoFragment, videoFragment;

    private FragmentTransaction transaction;

    private static String TAG_FRAG_PHOTO  = "ongoingFragment";
    private static String TAG_FRAG_VIDEO  = "pendingFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);

        //rl_pathhole = findViewById(R.id.rl_pathhole);
//
//        rl_pathhole.setDrawingCacheEnabled(true);
//
//        rl_pathhole.buildDrawingCache();
//
//        Bitmap bm = rl_pathhole.getDrawingCache();
//
        saveImageToInternalStorage(null);



        loadLibrary();

        photoFragment = Camera2BasicFragment2.newInstance();
        videoFragment = Video2BasicFragment2.newInstance();

        if (null == savedInstanceState) {

            showFragment(TAG_FRAG_PHOTO);


            /*getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment2.newInstance())
                    .commit();*/
        }
    }


    public boolean saveImageToInternalStorage(Bitmap image) {

       image = drawableToBitmap(getResources().getDrawable(R.drawable.round_pathhole2));

        try {

            File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),
                    VIDEO_DIRECTORY_NAME);
            // Create storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {

                    return false;
                }
            }

            File directory = new File(Environment.getExternalStorageDirectory(),
                    VIDEO_DIRECTORY_NAME);

            File mediaFile = new File((directory.getPath() + File.separator + "icon.png"));
            // Use the compress method on the Bitmap object to write image to
            // the OutputStream
            FileOutputStream fos = new FileOutputStream(mediaFile);;

            // Writing the bitmap to the output stream
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            return true;
        } catch (Exception e) {
           // Log.e("saveToInternalStorage()", e.getMessage());
            return false;
        }
    }



    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(480, 720, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    public void showPhotoFragment(){

        if(videoFragment != null && videoFragment.isVisible()){
            videoFragment.onPause();
            showFragment(TAG_FRAG_PHOTO);
        }

    }

    public void showVideoFragment(){
       /// Crashlytics.getInstance().crash();
        if(photoFragment != null && photoFragment.isVisible()){
            photoFragment.onPause();
            showFragment(TAG_FRAG_VIDEO);
        }
    }



    private void showFragment(String selectedFragment){
        if(selectedFragment.equals(TAG_FRAG_PHOTO)){

            transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (photoFragment.isAdded()) { // if the fragment is already in container
                transaction.show(photoFragment);
                photoFragment.onResume();

            } else { // fragment needs to be added to frame container
                transaction.add(R.id.container, photoFragment, TAG_FRAG_PHOTO);
            }
            // Hide fragment B
            if (videoFragment.isAdded()) {
                transaction.hide(videoFragment);
                /*
                 * in here we are clearing cached order numbers came via notification as because we already visited the
                 * pending fragment so that we already viewed the noti so we dont need this cache data anymore.
                 * */

            }

        }else if(selectedFragment.equals(TAG_FRAG_VIDEO)){
            transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (videoFragment.isAdded()) { // if the fragment is already in container
                transaction.show(videoFragment);
                videoFragment.onResume();
               // clearNotificationCounter();

            } else { // fragment needs to be added to frame container
                transaction.add(R.id.container, videoFragment, TAG_FRAG_VIDEO);
            }
            // Hide fragment B
            if (photoFragment.isAdded()) {
                transaction.hide(photoFragment);
            }
        }

        transaction.commit();

       // toggleBottomButtonColor(selectedFragment);
    }


    private void loadLibrary(){
        FFmpeg ffmpeg = FFmpeg.getInstance(this);
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                @Override
                public void onStart() {}

                @Override
                public void onFailure() {}

                @Override
                public void onSuccess() {}

                @Override
                public void onFinish() {}
            });
        } catch (FFmpegNotSupportedException e) {
            // Handle if FFmpeg is not supported by device
        }
    }
}
