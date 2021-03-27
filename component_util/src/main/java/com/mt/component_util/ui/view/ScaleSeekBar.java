package com.mt.component_util.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mt.component_util.R;

import androidx.appcompat.widget.AppCompatSeekBar;

public class ScaleSeekBar extends LinearLayout {
    private String[] postion ={"aa","bb","cc","dd","ee"};
    private int color = Color.parseColor("#000000");
    private int start_progress = 0;
    private int end_progress = 100;
    private SeekBar seekBar;
    public ScaleSeekBar(Context context) {
        this(context,null);
    }

    public ScaleSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScaleSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(attrs!=null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ScaleSeekBar);
            color = typedArray.getColor(R.styleable.ScaleSeekBar_text_color,Color.parseColor("#000000"));
            start_progress = typedArray.getInt(R.styleable.ScaleSeekBar_start_progress,0);
            end_progress = typedArray.getInt(R.styleable.ScaleSeekBar_end_progress,100);
            typedArray.recycle();
        }
        initUi();
    }
    private void initUi(){
        //添加一个seekBar
        seekBar = new SeekBar(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBar.setMin(start_progress);
            seekBar.setMax(end_progress);
        }
        this.setOrientation(VERTICAL);
        this.addView(seekBar);
        //再添加一个刻度标记
        LinearLayout contain  = new LinearLayout(getContext());
        contain.setOrientation(HORIZONTAL);
        contain.setWeightSum(postion.length);
        contain.setGravity(Gravity.CENTER);
        for(int i = 0;i<postion.length;i++){
            TextView textView = new TextView(getContext());
            textView.setTextColor(color);
            textView.setText(postion[i]);
            textView.setPadding(0,4,0,4);
            textView.setGravity(Gravity.CENTER);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
            textView.setLayoutParams(params);
            contain.addView(textView);
        }
        this.addView(contain);

        //设置滑动监听函数
        setScalewithPosition();
    }

    /**
     * 设置手指离开界面自动定位到某一个选项中
     */
    public void setScalewithPosition(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int curprogress = seekBar.getProgress();
                int jiange = (end_progress - start_progress)/(postion.length-1);
                int kedu = (end_progress-start_progress)/(postion.length-1)/2;
                int pos  =curprogress/jiange;
                if(curprogress%jiange>=kedu){
                    pos++;
                }

                seekBar.setProgress(pos*jiange);
            }
        });
    }


}
