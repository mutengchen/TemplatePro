package com.mt.component_util.ui.view;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.mt.component_util.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import androidx.annotation.Nullable;

/**
 * 打开相册工具类
 */
public class SystemAlbumUtil {
    public static int ALBUM_RESULT  =  0x1111;
    /**
     * 选择完成之后，返回数据的activtiy
     * @param activity
     */
    private static void openAlbum(Activity activity){
        Matisse.from(activity)
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(9)
                .gridExpectedSize(activity.getResources().getDimensionPixelSize(R.dimen.grid_width))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .showPreview(false) // Default is `true`
                .forResult(ALBUM_RESULT);
    }
    /**
     * 在onActivityResult里面获取对应的api
     * List<Uri> uris = matisse.obtainResult(data);
     */
}
