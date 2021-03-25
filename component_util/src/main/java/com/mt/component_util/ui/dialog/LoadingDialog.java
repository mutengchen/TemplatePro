package com.mt.component_util.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.mt.component_util.R;
import com.mt.component_util.utils.DensityUtils;

import androidx.annotation.NonNull;

public class LoadingDialog extends Dialog {
    private Context context;
    public LoadingDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.loading_dialog,null,false);
        setContentView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCanceledOnTouchOutside(false);
        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.height = (int) (DensityUtils.dip2px(context,140));
        lp.width = (int) (DensityUtils.dip2px(context,200));
        lp.dimAmount =0f;
        win.setAttributes(lp);
    }
}