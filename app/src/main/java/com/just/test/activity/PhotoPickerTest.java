package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.just.test.R;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;
import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * photoPicker  仿微信选择图片 包括拍照，单选，多选，预览等
 * http://blog.csdn.net/zhaihaohao1/article/details/51614757
 * compile 'com.foamtrace:photopicker:1.0'
 * https://github.com/liuling07/PhotoPicker
 * Created by admin on 2017/6/28.
 */

public class PhotoPickerTest extends Activity implements View.OnClickListener{

    private Button btn_singlecheck,btn_multicheck,btn_startPhoto;
    private static final int SINGLECHECK = 0;
    private static final int MULTICHECK = 1;
    private static final int TAKEPHOTO = 2;
    private static final int PREVIEW = 3;
    private ImageCaptureManager manager;
    private ArrayList<String> imgPath = null;
    private GridView gridview_photopicker;
    private PhotoPickerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_photopicker);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "仿微信选择图片");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initView();
    }

    private void initView() {
        btn_singlecheck = (Button) findViewById(R.id.btn_singlecheck);
        btn_multicheck = (Button) findViewById(R.id.btn_multicheck);
        btn_startPhoto = (Button) findViewById(R.id.btn_startPhoto);
        gridview_photopicker = (GridView)findViewById(R.id.gridview_photopicker);
        gridview_photopicker.setNumColumns(2);

        btn_singlecheck.setOnClickListener(this);
        btn_multicheck.setOnClickListener(this);
        btn_startPhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_singlecheck){
            PhotoPickerIntent intent = new PhotoPickerIntent(PhotoPickerTest.this);
            intent.setSelectModel(SelectModel.SINGLE);//设置选择模式
            intent.setShowCarema(true);//设置是否显示拍照，默认不显示
            startActivityForResult(intent,SINGLECHECK);
        }else if (view == btn_multicheck){
            PhotoPickerIntent intent = new PhotoPickerIntent(PhotoPickerTest.this);
            intent.setSelectModel(SelectModel.MULTI);//多选
            intent.setShowCarema(true);//设置是否显示拍照
            intent.setMaxTotal(9);//设置最多选择照片的张数，默认为9
            intent.setSelectedPaths(imgPath);
            startActivityForResult(intent,MULTICHECK);
        }else if (view == btn_startPhoto){
            try {
                manager = new ImageCaptureManager(PhotoPickerTest.this);
                Intent intent = manager.dispatchTakePictureIntent();
                startActivityForResult(intent,TAKEPHOTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadAdapter(ArrayList<String> paths){
        if (imgPath == null){
            imgPath = new ArrayList<>();
        }

        if (paths != null) {
            imgPath.clear();
            imgPath.addAll(paths);
        }

        if (adapter == null) {
            adapter = new PhotoPickerAdapter(PhotoPickerTest.this, imgPath, R.layout.item_photopicker);
            gridview_photopicker.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
        gridview_photopicker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PhotoPreviewIntent intent = new PhotoPreviewIntent(PhotoPickerTest.this);
                intent.setCurrentItem(i);
                intent.setPhotoPaths(imgPath);
                startActivityForResult(intent,PREVIEW);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case TAKEPHOTO://拍照返回
                    if (manager.getCurrentPhotoPath() != null){
                        manager.galleryAddPic();
                        ArrayList<String> path = new ArrayList<>();
                        path.add(manager.getCurrentPhotoPath());
                        loadAdapter(path);
                    }
                    break;
                case SINGLECHECK://单选返回
                    loadAdapter(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                    break;
                case MULTICHECK://多选返回
                    loadAdapter(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                    break;
                case PREVIEW://预览返回
                    loadAdapter(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                    break;
            }
        }
    }

    private class PhotoPickerAdapter extends CommonAdapter<String>{

        private PhotoPickerAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item) {
            ImageView imageView = helper.getView(R.id.iv_item_photopicker);

            Glide.with(PhotoPickerTest.this)
                    .load(item)
                    .override(500,600)
                    .centerCrop()
                    .error(R.drawable.empty_photo)
                    .into(imageView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (imgPath != null){
            imgPath.clear();
            imgPath = null;
        }
    }
}
