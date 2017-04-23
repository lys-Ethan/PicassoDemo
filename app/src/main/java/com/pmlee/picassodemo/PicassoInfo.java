package com.pmlee.picassodemo;

import android.content.Context;

import com.pmlee.picassodemo.util.FileUtils;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by liyunshuang on 2017/4/18.
 * <p>
 * Email 522940943@qq.com or liyunshuang21@gmail.com
 *
 * 该类用于保存创建的Picasso和部分设置，好同步NetworkImageLoadPresenter中的删除和查询功能
 */

public class PicassoInfo {
    //缓存名
    private static final String MY_CACHE_NAME = "my-image-cache";
    private static final String DEFAULT_CACHE = "picasso-cache";
    //自定义Picasso
    public Picasso mPicasso;
    //下载器中缓存的文件
    public File mCacheFile;

    public Downloader mDownloader;

    private PicassoInfo(Picasso mPicasso, File cacheFile, Downloader downloader) {
        this.mPicasso = mPicasso;
        this.mCacheFile = cacheFile;
        this.mDownloader = downloader;
    }

    /**
     * 构建默认的 PicassoInfo 包含默认缓存路径
     *
     * @param context
     * @return PicassoInfo
     */
    public static PicassoInfo getDefaultInfo(Context context) {
        //定义缓存路径
        File cacheFile = new File(FileUtils.getCacheDir(context), MY_CACHE_NAME);
        //内置下载器
        OkHttpDownloader downloader = new OkHttpDownloader(cacheFile);

        Picasso singeltonPicasso = new Picasso.Builder(context)
                //重新定义下载器，指定缓存路径
                .downloader(downloader)
                //log日志开启
                .loggingEnabled(true)
                .build();
        return new PicassoInfo(singeltonPicasso, cacheFile, downloader);
    }

    /**
     * 传入自定义的Picasso相关设置
     * 包括缓存的位置 自定义下载器 监听器
     *
     * @param context
     * @param downloader
     * @param cacheFile 该缓存路径为Downloader中所设置的，如果Downloader为null则该cacheFile无效，还是
     *                  为Picasso默认的路径，也会导致NetworkImageLoadPresenter中清理缓存和查询缓存失效.
     * @param listener
     * @return
     */
    public static PicassoInfo createPicassoInfo(Context context, Downloader downloader,
                                                File cacheFile, Picasso.Listener listener) {

        Picasso.Builder builder = new Picasso.Builder(context);
        if (listener != null)
            builder.listener(listener);
        if (downloader != null)
            builder.downloader(downloader);
        if (cacheFile == null||downloader== null) {
            cacheFile = new File(context.getCacheDir(), DEFAULT_CACHE);
        }

        return new PicassoInfo(builder.build(), cacheFile, downloader);
    }


}
