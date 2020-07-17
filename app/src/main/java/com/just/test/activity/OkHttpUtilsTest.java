package com.just.test.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.io.File;

import okhttp3.Call;

/**
 * okhttputils的封装使用，又名okgo
 * 具体可参考简书：http://www.jianshu.com/p/4c17956fe3b4
 * github：https://github.com/jeasonlzy/okhttp-OkGo
 * Created by admin on 2017/6/2.
 */

public class OkHttpUtilsTest extends Activity implements View.OnClickListener {

    private Button get, post, image, download;
    private TextView result, txt_okhttpUtils_download;
    private ImageView pic;
    private ProgressBar pb_okhttpUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_okhttp_utils);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " okhttputils的封装使用");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initView();
    }

    private void initView() {
        get = (Button) findViewById(R.id.btn_okhttpUtils_get);
        post = (Button) findViewById(R.id.btn_okhttpUtils_post);
        image = (Button) findViewById(R.id.btn_okhttpUtils_image);
        download = (Button) findViewById(R.id.btn_okhttpUtils_download);
        result = (TextView) findViewById(R.id.txt_okhttpUtils_string);
        pic = (ImageView) findViewById(R.id.iv_okhttpUtils_pic);
        pb_okhttpUtils = (ProgressBar) findViewById(R.id.pb_okhttpUtils);
        pb_okhttpUtils.setVisibility(View.GONE);
        txt_okhttpUtils_download = (TextView) findViewById(R.id.txt_okhttpUtils_download);
        txt_okhttpUtils_download.setVisibility(View.GONE);

        get.setOnClickListener(this);
        post.setOnClickListener(this);
        image.setOnClickListener(this);
        download.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == get) {
            queryGet();
        } else if (view == post) {
            LemonBubble.showRight(OkHttpUtilsTest.this, "正在努力开发中", 2000);
        } else if (view == image) {
            queryPic();
        } else if (view == download) {
            queryDownLoad();
        }
    }

    /**
     * get请求文本数据
     */
    private void queryGet() {
        OkHttpUtils.get().url("http://api.avatardata.cn/Joke/NewstJoke?key=0e10332d126f47e0b1729c80044b2e6d&page=1&rows=10")
                .tag(this).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                result.setText(response);
            }
        });
    }

    /**
     * 加载图片
     */
    private void queryPic() {
        OkHttpUtils.get().url("http://api.avatardata.cn/Joke/Img?file=005e233dd4764aa99e8e7d68568b9818.jpg")
                .tag(this).build().execute(new BitmapCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(Bitmap response, int id) {
                pic.setImageBitmap(response);
            }
        });
    }

    /**
     * 下载文件
     */
    private void queryDownLoad() {
        String url = "http://music.baidu.com/cms/BaiduMusic-pcwebdownload.apk";
        String versionName = "BaiduMusic-pcwebdownload.apk";
        OkHttpUtils.get().url(url).tag(this).build().execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), versionName) {

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                pb_okhttpUtils.setVisibility(View.VISIBLE);
                pb_okhttpUtils.setMax((int) total);
                pb_okhttpUtils.setProgress((int) progress);
                txt_okhttpUtils_download.setVisibility(View.VISIBLE);
                float speed = total / 1024;
                txt_okhttpUtils_download.setText("下载进度:" + progress / 1024 + "KB/" + total / 1024 + "KB-----当前网速：" + speed + "KB/s");
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(File response, int id) {
                LemonBubble.showRight(OkHttpUtilsTest.this, "下载完成:" + response.getAbsolutePath(), 5000);
                pb_okhttpUtils.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        //根据 Tag 取消请求
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
