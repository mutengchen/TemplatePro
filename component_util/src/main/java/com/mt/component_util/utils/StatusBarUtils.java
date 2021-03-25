package com.mt.component_util.utils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
public class StatusBarUtils {


    //为 Activity 的状态栏设置颜色.
    @SuppressLint("ObsoleteSdkInt")
    public static void setStatusBarColor(Activity activity, int color) {
        //处理 5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //直接调用系统自带的
            activity.getWindow().setStatusBarColor(color);
        }
        //处理 4.4 - 5.0, 没办法调用上面的方法, 需要用一个技巧
        //先设置为全屏, 然后在状态栏的位置加一个布局, 代替状态栏.
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //先设置为全屏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //创建自己的 statusBar
            View view = new View(activity);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity)));
            view.setBackgroundColor(color);
            //将我们创建的statusBar 赋值给 viewGroup
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(view, 0);

            //拿到 根布局中的 contentView
            ViewGroup contentView = activity.findViewById(android.R.id.content);
            //获取到我们 set 进去的布局
            View activityView = contentView.getChildAt(0);
            //设置属性
            activityView.setFitsSystemWindows(true);

        }
    }

    private static int getStatusBarHeight(Activity activity) {
        Resources rs = activity.getResources();
        //获取资源ID
        int statusBarHeightId = rs.getIdentifier("status_bar_height", "dimen", "android");
        //再根据ID 获取资源
        return rs.getDimensionPixelOffset(statusBarHeightId);
    }

    /**
     * 设置 Activity 全屏
     *
     * @param activity
     */
    public static void setActivityTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


}