package com.just.test.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * PhotoView
 https://github.com/chrisbanes/PhotoView
 * photoview 图片可放大缩小
 * Created by admin on 2017/4/5.
 */

public class PhotoViewActivity extends Activity {

    private PhotoView iv_photoview1,iv_photoview2,iv_photoview3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_photoview);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "PhotoView");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);
        //1
        iv_photoview1 = (PhotoView)findViewById(R.id.iv_photoview1);
        iv_photoview2 = (PhotoView)findViewById(R.id.iv_photoview2);
        iv_photoview3 = (PhotoView)findViewById(R.id.iv_photoview3);
        //2加载本地图片
        loadLocalPic();
        //3,设置不能缩放
        PhotoViewAttacher attacher = new PhotoViewAttacher(iv_photoview3);
        attacher.setZoomable(false);
    }

    private void loadLocalPic(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.a2);
        iv_photoview2.setImageBitmap(bitmap);
    }
}
