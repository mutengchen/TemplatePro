package com.mt.templatepro.api.exception;

import java.io.IOException;

import androidx.annotation.Nullable;

// 版本太久的异常
public class VersionTooOldException extends IOException {
    @Nullable
    @Override
    public String getMessage() {
        //是否记录在日志中
        return super.getMessage();
    }
}
