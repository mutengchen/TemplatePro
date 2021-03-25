package com.mt.component_util.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.mt.component_util.R;


/**
 * Date: 2020/1/6
 * Time: 10:27
 * author: cmt
 * desc: 播放语音工具类
 */
public class AudioUtils {
    private static MediaPlayer player;
    private static String TAG = AudioUtils.class.getSimpleName();
    public static void playRawVoice(Context context, int rawResId){
        Log.d(AudioUtils.class.getSimpleName(), "playRawVoice: "+rawResId);
        if(player!= null){
            if(player.isPlaying()){
                Log.d(TAG, "playRawVoice: other voice playing!");
                return;
            }
        }
        player = MediaPlayer.create(context,rawResId);
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.reset();
                mediaPlayer.release();
                player = null;
            }
        });
        player.start();
    }




    /**
     * 播放提示音
     *
     * @param type 0表示网络状态好，1表示网络连接中断 2表示开始录音，3表示结束录音,4表示录音太短无法发送
     */
    public static void playAlertVoice(Context context,int type,int voiceResId) {
        switch (type) {
            case 1:
                playRawVoice(context, voiceResId);
                break;
        }
        }

}