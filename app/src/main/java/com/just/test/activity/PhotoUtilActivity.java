package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.tools.PhotoUtils;
import com.just.test.widget.MyActionBar;

/**
 * 快速打开相机和相册
 * Created by admin on 2017/3/31.
 */

public class PhotoUtilActivity extends Activity implements View.OnClickListener{

    private Button btn_openCamera,btn_openAlbum;
    private ImageView iv_openCamera,iv_openAlbum;
    private Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_photoutils);
        mContext = this;
        initView();
    }

    private void initView() {
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "快速打开相机和相册");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        btn_openCamera = (Button)findViewById(R.id.btn_openCamera);
        btn_openAlbum = (Button)findViewById(R.id.btn_openAlbum);
        iv_openCamera = (ImageView)findViewById(R.id.iv_openCamera);
        iv_openAlbum = (ImageView)findViewById(R.id.iv_openAlbum);

        btn_openCamera.setOnClickListener(this);
        btn_openAlbum.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_openCamera){
            PhotoUtils.Open(mContext,PhotoUtils.CAMERA);
        }else if (view == btn_openAlbum){
            PhotoUtils.Open(mContext,PhotoUtils.ALBUM);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoUtils.onActivityResult(this,requestCode,resultCode,data);
        PhotoUtils.pushbitmap(new PhotoUtils.PictureCallback() {
            @Override
            public void imagePath(String imagepath) {

            }

            @Override
            public void HeadPortraitPath(String mHeadPortraitPath) {

            }

            @Override
            public void Bitmap(Bitmap bitMap) {
                iv_openCamera.setImageBitmap(bitMap);
            }
        });
    }
}
