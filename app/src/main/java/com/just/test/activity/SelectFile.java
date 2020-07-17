package com.just.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.leon.lfilepickerlibrary.LFilePicker;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.util.List;

/**
 * 选择文件
 * compile 'com.leon:lfilepickerlibrary:1.0'
 * 参考网址：http://www.apkbus.com/blog-897740-63476.html
 * https://github.com/leonHua/LFilePicker
 * Created by admin on 2017/6/3.
 */

public class SelectFile extends Activity {

    //返回图标风格
    public static final int BACKICON_STYLEONE = 0;
    public static final int BACKICON_STYLETWO = 1;
    public static final int BACKICON_STYLETHREE = 2;
    //图标风格
    public static final int ICON_STYLE_YELLOW = 0;
    public static final int ICON_STYLE_BLUE = 1;
    public static final int ICON_STYLE_GREEN = 2;

    public static String RESULT_INFO = "paths";
    private RadioButton rb_selectFile_bgY,rb_selectFile_bgG,rb_selectFile_bgB,rb_selectFile_icon1,rb_selectFile_icon2,rb_selectFile_icon3;
    private EditText et_selectFilm_filter;
    private RadioGroup rg_selectFile_bg,rg_selectFile_icon;
    private int bgType;
    private int iconType;
    private TextView txt_selectFilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_select_film);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 选择文件");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initView();
    }

    private void initView(){
        //背景颜色样式
        rg_selectFile_bg = (RadioGroup)findViewById(R.id.rg_selectFile_bg);
        rb_selectFile_bgY = (RadioButton)findViewById(R.id.rb_selectFile_bgY);
        rb_selectFile_bgG = (RadioButton)findViewById(R.id.rb_selectFile_bgG);
        rb_selectFile_bgB = (RadioButton)findViewById(R.id.rb_selectFile_bgB);
        //返回按钮样式
        rg_selectFile_icon = (RadioGroup)findViewById(R.id.rg_selectFile_icon);
        rb_selectFile_icon1 = (RadioButton)findViewById(R.id.rb_selectFile_icon1);
        rb_selectFile_icon2 = (RadioButton)findViewById(R.id.rb_selectFile_icon2);
        rb_selectFile_icon3 = (RadioButton)findViewById(R.id.rb_selectFile_icon3);
        //过滤器
        et_selectFilm_filter = (EditText)findViewById(R.id.et_selectFilm_filter);
        //跳转
        Button btn_selectFile_check = (Button) findViewById(R.id.btn_selectFile_check);
        //显示结果
        txt_selectFilm = (TextView)findViewById(R.id.txt_selectFilm);
        //背景颜色样式选择
        rg_selectFile_bg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch (checkId){
                    case R.id.rb_selectFile_bgY:
                        iconType = 0;
                        break;
                    case R.id.rb_selectFile_bgG:
                        iconType = 1;
                        break;
                    case R.id.rb_selectFile_bgB:
                        iconType = 2;
                        break;
                }
            }
        });
        //返回图标样式选择
        rg_selectFile_icon.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch (checkId){
                    case R.id.rb_selectFile_icon1:
                        bgType = BACKICON_STYLEONE;
                        break;
                    case R.id.rb_selectFile_icon2:
                        bgType = BACKICON_STYLETHREE;
                        break;
                    case R.id.rb_selectFile_icon3:
                        bgType = BACKICON_STYLETWO;
                        break;
                }
            }
        });


        btn_selectFile_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filter = et_selectFilm_filter.getText().toString();
                String [] colors = new String[]{"#FFD700","#7CFC00","#4169E1"};
                new LFilePicker().withActivity(SelectFile.this)
                        .withTitle("文件选择")
//                        .withIconStyle(iconType)
                        .withBackgroundColor(colors[iconType])
                        .withBackIcon(bgType)//返回按钮的样式
                        .withMutilyMode(true)//是否可多选
                        .withFileFilter(new String[]{filter})//过滤器，什么后缀的文件
                        .withRequestCode(1000).start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 1000){
                List<String> mList = data.getStringArrayListExtra(RESULT_INFO);
                String result = "";
                for (int i=0;i<mList.size();i++){
                    result += mList.get(i) + "\n";
                }
                txt_selectFilm.setText(result);
                LemonBubble.showRight(SelectFile.this,"选中了"+mList.size()+"个文件",3000);
            }
        }
    }

}
