package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.just.test.R;
import com.just.test.bean.FilmImage;
import com.just.test.sqlite.ImageDBHelper;
import com.just.test.tools.EditTextTools;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 电影海报数据库添加修改页
 * Created by admin on 2017/5/26.
 */

public class FilmImageDetail extends Activity implements View.OnClickListener{

    private EditText filmName,filmDate,inputurl;
    private Button imageLoading,filmSave;
    private ImageView filmPicture;
    private ImageDBHelper db;
    private boolean isLoading = false;//判断图片有没有加载出来
    private String styles;//类型：新增（0）或修改（1）
    private FilmImage filmImageDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_filmimage_detail);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "电影海报详情");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        db = new ImageDBHelper(FilmImageDetail.this);
        Intent intent = getIntent();
        styles = intent.getStringExtra("type");
        if (styles.equals("0")){
            //新建
        }else if (styles.equals("1")){
            String filmName = intent.getStringExtra("filmImage_id");
            filmImageDetail = db.checkFilmImage(filmName);
//            filmImageDetail = (FilmImage) bundle1.get("datas");
        }
        initView();
    }

    private void initView(){
        filmName = (EditText)findViewById(R.id.et_filmimage_detail_name);
        filmDate = (EditText)findViewById(R.id.et_filmimage_detail_date);
        inputurl = (EditText)findViewById(R.id.et_filmimage_detail_inputurl);
        filmPicture = (ImageView)findViewById(R.id.iv_filmimage_detail_picture);
        imageLoading = (Button)findViewById(R.id.btn_filmimage_detail_load);
        filmSave = (Button)findViewById(R.id.btn_filmimage_detail_save);

        if (styles.equals("1") && filmImageDetail != null){
            filmName.setText(filmImageDetail.getFilmImage_name());
            filmDate.setText(filmImageDetail.getFilmImage_date());
            inputurl.setText(filmImageDetail.getFilmImage_url());
            Glide.with(FilmImageDetail.this).load(filmImageDetail.getFilmImage_url()).into(filmPicture);
        }
        imageLoading.setOnClickListener(this);
        filmSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == imageLoading){
            EditTextTools.hideSoftInput(FilmImageDetail.this);
            String url = inputurl.getText().toString();
            Pattern p = Pattern.compile("^(f|ht){1}(tp|tps):\\/\\/([\\w-]+\\.)+[\\w-]+(\\/[\\w- ./?%&=]*)?");
            Matcher m = p.matcher(url);
            if (m.matches() && (url.contains(".png") || url.contains(".jpg"))){
                Glide.with(FilmImageDetail.this).load(url).into(filmPicture);
                isLoading = true;
            }else {
                LemonBubble.showError(FilmImageDetail.this,"图片路径不合法",3000);
            }
        }else if (view == filmSave){
            if (!isLoading){
                LemonBubble.showError(FilmImageDetail.this,"请先加载电影海报",3000);
            }else {
                isLoading = false;

                if (filmName.equals("") && filmDate.equals("") && inputurl.equals("")){
                    LemonBubble.showError(FilmImageDetail.this,"请先填写相关数据",3000);
                }else {
                    FilmImage detail = new FilmImage();
                    detail.setFilmImage_name(filmName.getText().toString());
                    detail.setFilmImage_date(filmDate.getText().toString());
                    detail.setFilmImage_url(inputurl.getText().toString());
                    detail.setFilmImage_picture(getImgByte(filmPicture));

                    if (styles.equals("0")) {
                        db.insert(detail);
                    }else if (styles.equals("1")){
                        detail.setId(filmImageDetail.getId());
                        db.update(detail);
                    }
                    handler.sendEmptyMessage(0);
                }
            }
        }
    }

    /**
     * 获取图片控件的位图
     * @param imageView 图片展示控件
     * @return  控件获取到的位图
     */
    private byte[] getImgByte(ImageView imageView){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        imageView.setDrawingCacheEnabled(true);//可获取位图
        Bitmap bitmap = imageView.getDrawingCache();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,os);
        return os.toByteArray();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                if (styles.equals("0")) {
                    LemonBubble.showRight(FilmImageDetail.this, "保存成功", 2000);
                }else if (styles.equals("1")){
                    LemonBubble.showRight(FilmImageDetail.this,"修改成功",2000);
                }
                handler.sendEmptyMessage(1);
            }else if (msg.what == 1){
                Intent intent = new Intent(FilmImageDetail.this,ImageDBTest.class);
                setResult(0,intent);
                finish();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        if (styles != null){
            styles = null;
        }
        if (filmImageDetail != null){
            filmImageDetail = null;
        }
    }
}
