package com.example.android.camera2basic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


public class Camera2BasicFragment2 extends Camera2BaseFragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {



    public static Camera2BasicFragment2 newInstance() {
        return new Camera2BasicFragment2();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera2_basic_fragment2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextureView = (AutoFitTextureView) view.findViewById(R.id.texture);
        rl_container_circuler_pathhole = view.findViewById(R.id.rl_container_circuler_pathhole);

        tv_focus = view.findViewById(R.id.tv_focus);
        tv_focus.setOnClickListener(this);
        tv_exposure = view.findViewById(R.id.tv_exposure);
        tv_exposure.setOnClickListener(this);
        tv_white_balance = view.findViewById(R.id.tv_white_balance);
        tv_white_balance.setOnClickListener(this);

        tv_circle_show_hide = view.findViewById(R.id.tv_circle_show_hide);
        tv_circle_show_hide.setOnClickListener(this);
        updateUICircularPathHole(CIRCULAR_PATH_HOLE);

        img_take_picture = view.findViewById(R.id.img_take_picture);
        img_take_picture.setOnClickListener(this);



        rl_container_exposure_tuning_option = view.findViewById(R.id.rl_container_exposure_tuning_option);
        rl_container_focus_tuning_option = view.findViewById(R.id.rl_container_focus_tuning_option);
        rl_container_wb_tuning_option = view.findViewById(R.id.rl_container_wb_tuning_option);


        seekbar_focus = view.findViewById(R.id.seekbar_focus);
        seekbar_focus.setOnSeekBarChangeListener(this);
        seekbar_iso = view.findViewById(R.id.seekbar_iso);
        seekbar_iso.setOnSeekBarChangeListener(this);
        seekbar_bias = view.findViewById(R.id.seekbar_bias);
        seekbar_bias.setOnSeekBarChangeListener(this);
        seekbar_temp = view.findViewById(R.id.seekbar_temp);
        seekbar_temp.setOnSeekBarChangeListener(this);
        seekbar_tint = view.findViewById(R.id.seekbar_tint);
        seekbar_tint.setOnSeekBarChangeListener(this);

        tv_mode_auto_focus = rl_container_focus_tuning_option.findViewById(R.id.tv_auto);
        tv_mode_auto_focus.setOnClickListener(this);
        tv_mode_locked_focus = rl_container_focus_tuning_option.findViewById(R.id.tv_locked);
        tv_mode_locked_focus.setOnClickListener(this);
        tv_mode_custom_focus = rl_container_focus_tuning_option.findViewById(R.id.tv_custom);
        tv_mode_custom_focus.setOnClickListener(this);


        tv_mode_auto_exposure = rl_container_exposure_tuning_option.findViewById(R.id.tv_auto);
        tv_mode_auto_exposure.setOnClickListener(this);
        tv_mode_locked_exposure = rl_container_exposure_tuning_option.findViewById(R.id.tv_locked);
        tv_mode_locked_exposure.setOnClickListener(this);
        tv_mode_custom_exposure = rl_container_exposure_tuning_option.findViewById(R.id.tv_custom);
        tv_mode_custom_exposure.setOnClickListener(this);

        tv_mode_auto_wb = rl_container_wb_tuning_option.findViewById(R.id.tv_auto);
        tv_mode_auto_wb.setOnClickListener(this);
        tv_mode_locked_wb = rl_container_wb_tuning_option.findViewById(R.id.tv_locked);
        tv_mode_locked_wb.setOnClickListener(this);
        tv_mode_custom_wb = rl_container_wb_tuning_option.findViewById(R.id.tv_custom);
        tv_mode_custom_wb.setOnClickListener(this);

        initDefaultUi();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.img_take_picture){
            takePicture();
        }

        if(id == R.id.tv_focus){

            SELECTED_TUNE_CATEGORY = TUNE_CATEGORY_FOCUS;

            updateTuneCategoryUI(TUNE_CATEGORY_FOCUS);
            updateTuneOptionUi(TUNE_CATEGORY_FOCUS);
            configureSeekbar(TUNE_CATEGORY_FOCUS);
            updateModeUI(TUNE_MODE_FOCUS);

        }
        if(id == R.id.tv_exposure){

            SELECTED_TUNE_CATEGORY = TUNE_CATEGORY_EXPOSURE;

            updateTuneCategoryUI(TUNE_CATEGORY_EXPOSURE);
            updateTuneOptionUi(TUNE_CATEGORY_EXPOSURE);
            configureSeekbar(TUNE_CATEGORY_EXPOSURE);
            updateModeUI(TUNE_MODE_EXPOSURE);

        }
        if(id == R.id.tv_white_balance){

            SELECTED_TUNE_CATEGORY = TUNE_CATEGORY_WB;

            updateTuneCategoryUI(TUNE_CATEGORY_WB);
            updateTuneOptionUi(TUNE_CATEGORY_WB);
            configureSeekbar(TUNE_CATEGORY_WB);
            updateModeUI(TUNE_MODE_WB);

        }
        if(id == R.id.tv_auto){



            updateModeUI(MODE_TYPE_AUTO);

            if(SELECTED_TUNE_CATEGORY == TUNE_CATEGORY_FOCUS){
                TUNE_MODE_FOCUS = MODE_TYPE_AUTO;
            }else if(SELECTED_TUNE_CATEGORY == TUNE_CATEGORY_EXPOSURE){
                TUNE_MODE_EXPOSURE = MODE_TYPE_AUTO;
            }else if(SELECTED_TUNE_CATEGORY == TUNE_CATEGORY_WB){
                TUNE_MODE_WB = MODE_TYPE_AUTO;
            }

        }

        if(id == R.id.tv_locked){
            updateModeUI(MODE_TYPE_LOCKED);

            if(SELECTED_TUNE_CATEGORY == TUNE_CATEGORY_FOCUS){
                TUNE_MODE_FOCUS = MODE_TYPE_LOCKED;
            }else if(SELECTED_TUNE_CATEGORY == TUNE_CATEGORY_EXPOSURE){
                TUNE_MODE_EXPOSURE = MODE_TYPE_LOCKED;
            }else if(SELECTED_TUNE_CATEGORY == TUNE_CATEGORY_WB){
                TUNE_MODE_WB = MODE_TYPE_LOCKED;
            }
        }
        if(id == R.id.tv_custom){
            updateModeUI(MODE_TYPE_CUSTOM);

            if(SELECTED_TUNE_CATEGORY == TUNE_CATEGORY_FOCUS){
                TUNE_MODE_FOCUS = MODE_TYPE_CUSTOM;
            }else if(SELECTED_TUNE_CATEGORY == TUNE_CATEGORY_EXPOSURE){
                TUNE_MODE_EXPOSURE = MODE_TYPE_CUSTOM;
            }else if(SELECTED_TUNE_CATEGORY == TUNE_CATEGORY_WB){
                TUNE_MODE_WB = MODE_TYPE_CUSTOM;
            }
        }

        if(id == R.id.tv_circle_show_hide){
            if(CIRCULAR_PATH_HOLE == CIRCULAR_PATH_HOLE_TRUE){

                CIRCULAR_PATH_HOLE = CIRCULAR_PATH_HOLE_FALSE;
                updateUICircularPathHole(CIRCULAR_PATH_HOLE);


                //rl_container_circuler_pathhole.setVisibility(View.VISIBLE);

            }else if(CIRCULAR_PATH_HOLE == CIRCULAR_PATH_HOLE_FALSE){

                CIRCULAR_PATH_HOLE = CIRCULAR_PATH_HOLE_TRUE;
                updateUICircularPathHole(CIRCULAR_PATH_HOLE);


                //rl_container_circuler_pathhole.setVisibility(View.GONE);
            }
        }
    }

    private void initDefaultUi(){
        rl_container_focus_tuning_option.setVisibility(View.GONE);
        rl_container_exposure_tuning_option.setVisibility(View.GONE);
        rl_container_wb_tuning_option.setVisibility(View.GONE);
    }

    private void updateTuneCategoryUI(String type){
        switch (type){
            case TUNE_CATEGORY_FOCUS:
                rl_container_focus_tuning_option.setVisibility(View.VISIBLE);
                rl_container_exposure_tuning_option.setVisibility(View.GONE);
                rl_container_wb_tuning_option.setVisibility(View.GONE);
                break;

            case TUNE_CATEGORY_EXPOSURE:
                rl_container_focus_tuning_option.setVisibility(View.GONE);
                rl_container_exposure_tuning_option.setVisibility(View.VISIBLE);
                rl_container_wb_tuning_option.setVisibility(View.GONE);

                break;
            case TUNE_CATEGORY_WB:
                rl_container_focus_tuning_option.setVisibility(View.GONE);
                rl_container_exposure_tuning_option.setVisibility(View.GONE);
                rl_container_wb_tuning_option.setVisibility(View.VISIBLE);

                break;
        }
    }

    private void updateTuneOptionUi(String type){
        switch (type){
            case TUNE_CATEGORY_FOCUS:
                tv_focus.setTextColor(getResources().getColor(R.color.yellow));
                tv_white_balance.setTextColor(getResources().getColor(R.color.white));
                tv_exposure.setTextColor(getResources().getColor(R.color.white));


                break;

            case TUNE_CATEGORY_EXPOSURE:
                tv_focus.setTextColor(getResources().getColor(R.color.white));
                tv_white_balance.setTextColor(getResources().getColor(R.color.white));
                tv_exposure.setTextColor(getResources().getColor(R.color.yellow));

                break;
            case TUNE_CATEGORY_WB:
                tv_focus.setTextColor(getResources().getColor(R.color.white));
                tv_white_balance.setTextColor(getResources().getColor(R.color.yellow));
                tv_exposure.setTextColor(getResources().getColor(R.color.white));

                break;
        }
    }

    private void configureSeekbar(String type){
        switch (type){
            case TUNE_CATEGORY_FOCUS:
                seekbar_focus.setMax(focus_max);
                seekbar_focus.setProgress((int) focus_value);

                break;

            case TUNE_CATEGORY_EXPOSURE:
                seekbar_iso.setMax(iso_max);
                seekbar_iso.setProgress(iso_value);

                seekbar_bias.setMax(bias_max);
                seekbar_bias.setProgress(bias_value);

                break;
            case TUNE_CATEGORY_WB:

                seekbar_temp.setMax(temp_max);
                seekbar_temp.setProgress(temp_value);

                seekbar_tint.setMax(tint_max);
                seekbar_tint.setProgress(tint_value);
                break;
        }
    }



    private void updateModeUI(String modeType){
        switch (SELECTED_TUNE_CATEGORY){
            case TUNE_CATEGORY_FOCUS:

                createCameraPreviewSession_focus_mode(modeType);

                updateModeUI(modeType, tv_mode_auto_focus,tv_mode_locked_focus, tv_mode_custom_focus);


                break;

            case TUNE_CATEGORY_EXPOSURE:

                createCameraPreviewSession_exposure_mode(modeType);

                updateModeUI(modeType, tv_mode_auto_exposure,tv_mode_locked_exposure, tv_mode_custom_exposure);


                break;
            case TUNE_CATEGORY_WB:
                createCameraPreviewSession_wb_mode(modeType);

                updateModeUI(modeType, tv_mode_auto_wb,tv_mode_locked_wb, tv_mode_custom_wb);
                break;
        }
    }

    private void updateModeUI(String type, TextView tv_auto, TextView tv_locked, TextView tv_custom){
        switch (type){
            case MODE_TYPE_AUTO:
                tv_auto.setBackgroundResource(R.drawable.rounded_left_boarder_fill_white);
                tv_locked.setBackgroundResource(android.R.color.transparent);
                tv_custom.setBackgroundResource(android.R.color.transparent);

                tv_auto.setTextColor(getResources().getColor(R.color.gray_dark));
                tv_locked.setTextColor(getResources().getColor(R.color.white));
                tv_custom.setTextColor(getResources().getColor(R.color.white));


                break;
            case MODE_TYPE_LOCKED:
                tv_auto.setBackgroundResource(android.R.color.transparent);
                tv_locked.setBackgroundResource(R.drawable.rounded_square_boarder_fill_white);
                tv_custom.setBackgroundResource(android.R.color.transparent);

                tv_auto.setTextColor(getResources().getColor(R.color.white));
                tv_locked.setTextColor(getResources().getColor(R.color.gray_dark));
                tv_custom.setTextColor(getResources().getColor(R.color.white));


                break;
            case MODE_TYPE_CUSTOM:
                tv_auto.setBackgroundResource(android.R.color.transparent);
                tv_locked.setBackgroundResource(android.R.color.transparent);
                tv_custom.setBackgroundResource(R.drawable.rounded_right_boarder_fill_white);

                tv_auto.setTextColor(getResources().getColor(R.color.white));
                tv_locked.setTextColor(getResources().getColor(R.color.white));
                tv_custom.setTextColor(getResources().getColor(R.color.gray_dark));
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        switch (seekBar.getId()) {
            case R.id.seekbar_focus:
                focus_value = progress/10.0f;
                createCameraPreviewSession_focus_change();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.seekbar_iso:
                iso_value = seekBar.getProgress();
                //tv_iso.setText(""+seekIso);
                createCameraPreviewSession_iso_change();
                break;
            case R.id.seekbar_ss:
                //value = (seekBar.getProgress() + 9516);
               // tv_ss.setText(""+seekSs);
                createCameraPreviewSession_ss_chnage();
                break;
            case R.id.seekbar_temp:
                //seekSs = (seekBar.getProgress() + 9516);
                temp_value = seekBar.getProgress() + 3000;
                // tv_ss.setText(""+seekSs);
                createCameraPreviewSession_temp_chnage();
                break;
            default:
                break;
        }
    }


    private void updateUICircularPathHole(int show_or_hide){
        if(show_or_hide == CIRCULAR_PATH_HOLE_TRUE){
            rl_container_circuler_pathhole.setVisibility(View.VISIBLE);

        }else if(show_or_hide == CIRCULAR_PATH_HOLE_FALSE){
            rl_container_circuler_pathhole.setVisibility(View.GONE);
        }
    }
}
