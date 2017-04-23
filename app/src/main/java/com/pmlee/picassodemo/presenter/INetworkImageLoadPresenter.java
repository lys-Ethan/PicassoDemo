package com.pmlee.picassodemo.presenter;

import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.RequestCreator;

/**
 * Created by liyunshuang on 2016/11/18.
 * <p>
 * Email 522940943@qq.com or liyunshuang21@gmail.com
 */

public interface INetworkImageLoadPresenter {
    /**
     * 步加载图片
     * 加载默认原图片大小
     * @param iv
     * @param url
     */
    void loadImage(ImageView iv, String url);

    /**
     * 步加载图片
     * 自定义Picasso请求参数
     * @param iv
     * @param requestCreator
     */
    void loadImage(ImageView iv, RequestCreator requestCreator);

    /**
     * 异步加载图片
     *
     * @param iv 目标ImageView
     * @param url 图片URL地址
     * @param height 指定高度
     * @param width 指定宽度  单位 px
     *
     *              缩放方式为CenterCrop
     */
    void loadImage(ImageView iv, String url, int width, int height);

    /**
     * 带有监听回调的 图片加载
     * com.squareup.picasso.callback
     * @param iv
     * @param url
     * @param width
     * @param height
     * @param callback
     */
    void loadImage(ImageView iv, String url, int width, int height, Callback callback);

    void loadImage(ImageView iv, String url, boolean showError);

    /**
     * 加载并处理为圆形图片
     * @param iv
     * @param url
     */
    void loadCircleImage(ImageView iv, String url);

    /**
     * 计算缓存大小
     * @return 带单位的数值 最小单位为 bit 最大为 GB
     */
    String calculateCacheSize();

    /**
     * 清除所有的缓存
     */
    void cleanCacheAll();

    /**
     * 删除请求过的缓存
     * 针对于头像更新等操作
     * @param requestedUrl
     */
    void cleanCache(String requestedUrl);

    /**
     * 获取Picasso请求构建器
     * @param url
     * @return
     */
    RequestCreator getRequestCreator(String url);



}
