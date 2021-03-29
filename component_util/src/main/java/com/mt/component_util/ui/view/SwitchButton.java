package com.mt.component_util.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AnimationSet;

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
    float ball_x;
    RectF rectF;
    CheckListener checkListener;

    public SwitchButton(Context context) {
        this(context,null);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton);
        bgColor = typedArray.getColor(R.styleable.SwitchButton_bg_color,Color.parseColor("#999999"));
        ballColor = typedArray.getColor(R.styleable.SwitchButton_ball_color,Color.parseColor("#1AAC19"));
        typedArray.recycle();
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
        drawSwitchBg(canvas);
        drawSwitchBall(canvas);

    }
    private void drawSwitchBall(Canvas canvas){
        canvas.drawCircle(ball_x,mStrokeRadius,mSolidRadius,ballPaint);
    }
    private void drawSwitchBg(Canvas canvas){
        canvas.drawRoundRect(rectF,mStrokeRadius,mSolidRadius,bgPaint);
    }

    private void initData(){
        ballPaint = new Paint();
        ballPaint.setColor(ballColor);
        ballPaint.setAntiAlias(true);
        ballPaint.setStyle(Paint.Style.FILL);

        bgPaint = new Paint();
        bgPaint.setColor(bgColor);
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mCurrentState = (mCurrentState == State.CLOSE?State.OPEN:State.CLOSE);
        if(mCurrentState ==State.CLOSE)
            animate(ball_x_right,mStrokeRadius,Color.parseColor("#1AAc19"),Color.parseColor("#999999"));
        else
            animate(mStrokeRadius,ball_x_right,Color.parseColor("#999999"),Color.parseColor("#1AAc19"));

        if(checkListener != null){
            if(mCurrentState == State.OPEN){
                checkListener.onCheckChanged(this,true);
            }else
                checkListener.onCheckChanged(this,false);
        }


    }
    private void animate(int from,int to,int startColor,int endColor){
        ValueAnimator translate = ValueAnimator.ofFloat(from,to);
        translate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //动画执行完成之后，记录球停留的位置的x
                ball_x = (float) valueAnimator.getAnimatedValue();
                postInvalidate();

            }
        });
        ValueAnimator color = ValueAnimator.ofObject(new TypeEvaluator() {
            @Override
            public Object evaluate(float v, Object o, Object t1) {
                if(v==0)
                    return o;
                else
                    return t1;
            }
        },startColor,endColor);
        color.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                bgColor = (int) valueAnimator.getAnimatedValue();
                bgPaint.setColor(bgColor);
                postInvalidate();
            }
        });

        //执行动画了
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translate,color);
        animatorSet.setDuration(200);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setClickable(true);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                setClickable(false);
            }
        });
        animatorSet.start();
    }
    private enum State{
        OPEN,CLOSE
    }
    private State mCurrentState;
    public interface CheckListener{
        void onCheckChanged(View view,boolean status);
    }
}
