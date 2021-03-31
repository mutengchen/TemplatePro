package com.mt.mCamView;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 视频录制view,支持录制视频和切换视频播放预览
 *
 */
public class VideoRecordView extends CameraSurfaceView{
    public VideoRecordView(Context context) {
        super(context);
    }

    public VideoRecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoRecordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
