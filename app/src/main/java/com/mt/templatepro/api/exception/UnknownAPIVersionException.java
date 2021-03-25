package com.mt.templatepro.api.exception;

import java.io.IOException;

import androidx.annotation.Nullable;

// API版本异常
public class UnknownAPIVersionException extends IOException {
    @Nullable
    @Override
    public String getMessage() {
        //是否记录在日志中
        return super.getMessage();
    }
}
