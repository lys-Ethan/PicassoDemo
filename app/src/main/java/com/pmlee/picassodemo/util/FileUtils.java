package com.pmlee.picassodemo.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by liyunshuang on 2016/11/11.
 */

public class FileUtils {
    //根据手机状态获取存储根路径
    public static String getCacheDir(Context context) {

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getAbsolutePath();
        } else {
            return context.getCacheDir().getAbsolutePath();
        }

    }

    // 递归方式 计算文件的大小
    public static long getTotalSizeOfFilesInDir(final File file) {
        if (file.isFile())
            return file.length();
        final File[] children = file.listFiles();
        long total = 0;
        if (children != null)
            for (final File child : children)
                total += getTotalSizeOfFilesInDir(child);
        return total;
    }
}
