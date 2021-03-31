package com.mt.mCamView;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    public CameraSurfaceView(Context context) {
        super(context);
        init();
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        CameraUtils.openCamera(Camera.CameraInfo.CAMERA_FACING_BACK,CameraUtils.DESIRED_PREVIEW_FPS);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        CameraUtils.startPreviewDisplay(holder);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        CameraUtils.releaseCamera();
    }
    private void init(){
            surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
    }
}
