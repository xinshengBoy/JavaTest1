package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.bean.FilmDetail;
import com.just.test.sqlite.FilmListDBHelper;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * 电影列表详情
 * 新增和修改
 * 如果styles为0是新增，1是修改
 * Created by admin on 2017/5/24.
 */

public class FilmDetailTest extends Activity implements View.OnClickListener{

    private EditText name,type,director,star,filmlong,country,date,url,info;
    private Button save;
    private FilmListDBHelper db;
    private FilmDetail filmDetail;
    private String styles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_filmdetail);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "电影列表详情");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        db = new FilmListDBHelper(FilmDetailTest.this);

        Intent intent = getIntent();
        styles = intent.getStringExtra("type");
        if (styles.equals("0")){
            //新建
        }else if (styles.equals("1")){
            //修改
            Bundle bundle1 = intent.getExtras();
            filmDetail = (FilmDetail) bundle1.get("datas");
        }
        initView();
    }

    private void initView(){
        name = (EditText)findViewById(R.id.et_filmdetail_name);
        type = (EditText)findViewById(R.id.et_filmdetail_type);
        director = (EditText)findViewById(R.id.et_filmdetail_director);
        star = (EditText)findViewById(R.id.et_filmdetail_star);
        filmlong = (EditText)findViewById(R.id.et_filmdetail_long);
        country = (EditText)findViewById(R.id.et_filmdetail_country);
        date = (EditText)findViewById(R.id.et_filmdetail_date);
        url = (EditText)findViewById(R.id.et_filmdetail_url);
        info = (EditText)findViewById(R.id.et_filmdetail_info);
        save = (Button)findViewById(R.id.btn_filmdetail_save);

        if (styles.equals("1") && filmDetail != null){
            name.setText(filmDetail.getFilmname());
            type.setText(filmDetail.getFilmtype());
            director.setText(filmDetail.getFilmdirector());
            star.setText(filmDetail.getFilmstar());
            filmlong.setText(filmDetail.getFilmlong());
            country.setText(filmDetail.getFilmcountry());
            date.setText(filmDetail.getFilmdate());
            url.setText(filmDetail.getFilmurl());
            info.setText(filmDetail.getFilminfo());
        }
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == save){
            if (name.getText().toString().equals("") || type.getText().toString().equals("") || director.getText().toString().equals("") || star.getText().toString().equals("") ||
                    filmlong.getText().toString().equals("") || country.getText().toString().equals("") || date.getText().toString().equals("") || url.getText().toString().equals("") ||
                    info.getText().toString().equals("")){
                LemonBubble.showError(FilmDetailTest.this,"不能为空！",3000);
            }else {
                FilmDetail detail = new FilmDetail();
                detail.setFilmname(name.getText().toString());
                detail.setFilmtype(type.getText().toString());
                detail.setFilmdirector(director.getText().toString());
                detail.setFilmstar(star.getText().toString());
                detail.setFilmlong(filmlong.getText().toString());
                detail.setFilmcountry(country.getText().toString());
                detail.setFilmdate(date.getText().toString());
                detail.setFilmurl(url.getText().toString());
                detail.setFilminfo(info.getText().toString());

                if (styles.equals("0")){
                    db.insert(detail);
                }else if (styles.equals("1")){
                    detail.setId(filmDetail.getId());
                    db.update(detail);
                }
                handler.sendEmptyMessage(0);
            }
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                if (styles.equals("0")) {
                    LemonBubble.showRight(FilmDetailTest.this, "保存成功", 2000);
                }else if (styles.equals("1")){
                    LemonBubble.showRight(FilmDetailTest.this, "修改成功", 2000);
                }
                Intent intent = new Intent(FilmDetailTest.this,FilmListDBTest.class);
                setResult(0,intent);
                FilmDetailTest.this.finish();
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
        if (filmDetail != null){
            filmDetail = null;
        }
    }
}
