package com.pmlee.picassodemo;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pmlee.picassodemo.presenter.INetworkImageLoadPresenter;
import com.pmlee.picassodemo.presenter.NetworkImageLoadPresenter;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;

public class MainActivity extends Activity implements View.OnClickListener {
    private ImageView imageView;
    private Button btn_load;
    private Button btn_cleanCache;
    private Button btn_cleanAllCache;
    private Button btn_calculate;
    private String url = "http://i.imgur.com/DvpvklR.png";
    private NetworkImageLoadPresenter imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        preData();
    }

    private void preData() {
        //默认加载
        //imageLoader = NetworkImageLoadPresenter.create(this);
        //自定义Picasso
        File cacheFile = new File(getExternalCacheDir(),"cache-test");
        imageLoader = NetworkImageLoadPresenter.create(this,PicassoInfo.createPicassoInfo(this,
                new OkHttpDownloader(cacheFile),cacheFile,null));
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.picassoView);
        btn_load = (Button) findViewById(R.id.loadImage);
        btn_load.setOnClickListener(this);
        btn_cleanCache = (Button) findViewById(R.id.cleanCurrent);
        btn_cleanCache.setOnClickListener(this);
        btn_cleanAllCache = (Button) findViewById(R.id.cleanAll);
        btn_cleanAllCache.setOnClickListener(this);
        btn_calculate = (Button) findViewById(R.id.calculateCache);
        btn_calculate.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loadImage:
                imageLoader.loadImage(imageView,url);
                break;
            case R.id.cleanCurrent:
                imageLoader.cleanCache(url);
                break;
            case R.id.cleanAll:
                imageLoader.cleanCacheAll();
                break;
            case R.id.calculateCache:
                Log.i("picasso-demo","缓存大小："+imageLoader.calculateCacheSize());
                break;
        }
    }
}
