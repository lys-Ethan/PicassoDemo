package com.pmlee.picassodemo.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;

import com.pmlee.picassodemo.PicassoInfo;
import com.pmlee.picassodemo.R;
import com.pmlee.picassodemo.util.CircleTransform;
import com.pmlee.picassodemo.util.FileUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

/**
 * Created by liyunshuang on 2016/11/18.
 * <p>
 * Email 522940943@qq.com or liyunshuang21@gmail.com
 */

public class NetworkImageLoadPresenter implements INetworkImageLoadPresenter {
    //测试标签
    private static final String TAG = "NetworkImageLoad";
    private static Context mContext;
    //缓存信息
    private File cacheFile;
    private Picasso mSingletonPicasso;
    private PicassoInfo mPicassoInfo;
    private static NetworkImageLoadPresenter customLoader;

    private NetworkImageLoadPresenter(Context context) {
        this(context, null);
    }


    /**
     * 传入 自定义Picasso的构造器
     *
     * @param context      Context
     * @param mPicassoInfo PicassoInfo
     */
    private NetworkImageLoadPresenter(Context context, PicassoInfo mPicassoInfo) {
        mContext = context;
        this.mPicassoInfo = mPicassoInfo;
        this.mSingletonPicasso = mPicassoInfo.mPicasso;
        this.cacheFile = mPicassoInfo.mCacheFile;
        Picasso.setSingletonInstance(mSingletonPicasso);
    }

    /**
     * 默认数据加载
     */
    private static class SingletonImageLoadHolder {
        //获取默认的Picasso参数
        public static PicassoInfo info = PicassoInfo.getDefaultInfo(mContext);
        //实例化NetworkImageLoadPresenter类
        public static NetworkImageLoadPresenter loader = new NetworkImageLoadPresenter(mContext, info);

    }

    /**
     * 构建NetworkImageLoad实例
     *
     * @param context
     * @return NetworkImageLoad
     */
    public static NetworkImageLoadPresenter create(Context context) {
        if (context != null)
            mContext = context;
        else return null;
        return SingletonImageLoadHolder.loader;
    }

    /**
     * 构建NetworkImageLoad实例
     *
     * @param context
     * @param picassoInfo 自定义PicassoInfo
     * @return NetworkImageLoad
     */
    public static NetworkImageLoadPresenter create(Context context, PicassoInfo picassoInfo) {
        if (customLoader == null)
            customLoader = new NetworkImageLoadPresenter(context, picassoInfo);
        return customLoader;
    }


    @Override
    public void loadImage(@NonNull ImageView iv, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        getRequestCreator(url)
                .into(iv);

    }

    @Override
    public void loadImage(@NonNull ImageView iv, @NonNull RequestCreator requestCreator) {

        requestCreator.into(iv);
    }

    @Override
    public void loadImage(@NonNull ImageView iv, String url, int width, int height) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        RequestCreator requestCreator = getRequestCreator(url);
        requestCreator.resize(width, height)
                .centerCrop()
                .into(iv);
    }

    @Override
    public void loadImage(@NonNull ImageView iv, String url, int width, int height, Callback callback) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        RequestCreator requestCreator = getRequestCreator(url);
        if (callback != null)
            requestCreator.fetch(callback);
        requestCreator.resize(width, height)
                .centerCrop()
                .into(iv);
    }

    @Override
    public void loadImage(ImageView iv, String url, boolean showError) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        RequestCreator requestCreator = getRequestCreator(url);
        if (showError) {
//            requestCreator.error(mContext.getResources().getDrawable(R.mipmap.photo_fault));
        }
        requestCreator.networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE).into(iv);
    }

    @Override
    public void loadCircleImage(ImageView iv, String url) {
        if (TextUtils.isEmpty(url)) {
//            iv.setImageResource(R.mipmap.ic_launcher);
            return;
        }
        RequestCreator requestCreator = getRequestCreator(url);
        requestCreator.networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE).transform(
                new CircleTransform()).error(mContext.getResources().getDrawable(R.mipmap.ic_launcher))
                .into(iv);
    }


    @Override
    public RequestCreator getRequestCreator(String url) {
        return mSingletonPicasso.load(url);
    }

    @Override
    public String calculateCacheSize() {
        long total = FileUtils.getTotalSizeOfFilesInDir(cacheFile);
        return total >= 1024 && total < (1024 * 1024) ? (total >> 10) + " KB" :
                total >= (1024 * 1024) && total < (1024 * 1024 * 1024) ? (total >> 20) + " MB" :
                        total >= (1024 * 1024 * 1024) ? (total >> 30) + " GB" :
                                total + "B";
    }

    @Override
    public void cleanCacheAll() {
        if (cacheFile.exists() && cacheFile.isDirectory()) {
            for (File target : cacheFile.listFiles()) {
                target.delete();
            }
        }
    }

    @Override
    public void cleanCache(String requestedUrl) {
        if (mSingletonPicasso != null)
            mSingletonPicasso.invalidate(requestedUrl);
    }

    /**
     * 将Picasso进行返回 用于参数设置
     * @return
     */
    public Picasso getPicasso(){
        return mSingletonPicasso==null?Picasso.with(mContext):mSingletonPicasso;
    }


}
