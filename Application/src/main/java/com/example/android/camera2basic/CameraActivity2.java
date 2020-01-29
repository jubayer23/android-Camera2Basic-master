package com.example.android.camera2basic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.util.ArrayList;

public class CameraActivity2 extends AppCompatActivity {

    Fragment photoFragment, videoFragment;

    private FragmentTransaction transaction;

    private static String TAG_FRAG_PHOTO  = "ongoingFragment";
    private static String TAG_FRAG_VIDEO  = "pendingFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);

        photoFragment = Camera2BasicFragment2.newInstance();
        videoFragment = Video2BasicFragment2.newInstance();

        if (null == savedInstanceState) {

            showFragment(TAG_FRAG_PHOTO);


            /*getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment2.newInstance())
                    .commit();*/
        }
    }

    public void showPhotoFragment(){

        if(videoFragment != null && videoFragment.isVisible()){
            videoFragment.onPause();
            showFragment(TAG_FRAG_PHOTO);
        }

    }

    public void showVideoFragment(){
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
}
