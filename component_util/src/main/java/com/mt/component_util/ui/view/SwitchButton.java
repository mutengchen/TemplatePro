package com.mt.component_util.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.google.gson.TypeAdapter;
import com.mt.component_util.R;

import androidx.annotation.Nullable;

public class SwitchButton extends View implements View.OnClickListener {
    private static final int DEF_W =120;
    private static final int DEF_H = 50;
    Paint ballPaint;
    Paint bgPaint;
    int bgColor;
    int ballColor;
    int mViewHeight;
    int mViewWidth;
    float switchViewStrockWidth;
    int mStrokeRadius;
    float mSolidRadius;
    int ball_x_right;
    int ball_x;
    RectF rectF;


    public SwitchButton(Context context) {
        this(context,null);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton);
        initData();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewHeight = h;
        mViewWidth = w;
        //默认描边是控件宽度的1/30
        switchViewStrockWidth = w * 1.0f /30;
        //外圈圆的半径
        mStrokeRadius = mViewHeight /2;
        //内圈圆的半径
        mSolidRadius = (mViewHeight-2*switchViewStrockWidth)/2;
        ball_x_right =  mViewWidth - mStrokeRadius;
        ball_x = mStrokeRadius;
        //获取矩形
        rectF = new RectF(0,0,mViewWidth,mViewHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth;
        int measureHeight;
        //相当于不适用wrap_content 而是使用match_parent或者是具体的dp值
        switch(widthMode){
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.AT_MOST:
                measureWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,DEF_W,getResources().getDisplayMetrics());
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(measureWidth,MeasureSpec.EXACTLY);
                break;
                case MeasureSpec.EXACTLY:

        }

        switch(heightMode){
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.AT_MOST:
                measureHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,DEF_H,getResources().getDisplayMetrics());
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(measureHeight,MeasureSpec.EXACTLY);
                break;
            case MeasureSpec.EXACTLY:

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    //绘制打开的逻辑和机制了
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
    private void drawSwitchBall(Canvas canvas){
        canvas.drawCircle(ball_x,mStrokeRadius,mSolidRadius,ballPaint);
    }
    private void drawSwitchBg(Canvas canvas){
        canvas.drawRoundRect(rectF,mStrokeRadius,mSolidRadius,bgPaint);
    }

    private void initData(){
        ballPaint = new Paint();
        ballPaint.setColor(bgColor);
        ballPaint.setStyle(Paint.Style.FILL);

        bgPaint = new Paint();
        bgPaint.setColor(bgColor);
        bgPaint.setStyle(Paint.Style.FILL);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    private enum State{
        OPEN,CLOSE
    }
    private State mCurrentState;
}
