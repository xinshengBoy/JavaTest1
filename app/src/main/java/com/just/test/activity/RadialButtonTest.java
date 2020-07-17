package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.github.alexkolpa.fabtoolbar.FabToolbar;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * 可展开收缩的按钮菜单
 * https://github.com/mitchwongho/RadialButtons
 * Created by admin on 2017/6/15.
 * fab-toolbar   像Material设计指南中所描述那样，一个悬浮操作按钮的Toolbar库。
 * https://github.com/AlexKolpa/fab-toolbar
 * compile 'com.github.alexkolpa:floating-action-button-toolbar:0.5.1'
 */

public class RadialButtonTest extends Activity implements View.OnClickListener{

    private FabToolbar view_fabtoolbar;
    private ImageView iv_fabTool_create,iv_fabTool_refresh,iv_fabTool_delete,iv_fabTool_remove,iv_fabTool_search;
    private boolean isShow = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_radialbuttons);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 可展开收缩的按钮菜单");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initView();
    }

    private void initView(){
        view_fabtoolbar = (FabToolbar)findViewById(R.id.view_fabtoolbar);
        iv_fabTool_create = (ImageView)findViewById(R.id.iv_fabTool_create);
        iv_fabTool_refresh = (ImageView)findViewById(R.id.iv_fabTool_refresh);
        iv_fabTool_delete = (ImageView)findViewById(R.id.iv_fabTool_delete);
        iv_fabTool_remove = (ImageView)findViewById(R.id.iv_fabTool_remove);
        iv_fabTool_search = (ImageView)findViewById(R.id.iv_fabTool_search);

        view_fabtoolbar.setOnClickListener(this);
        iv_fabTool_create.setOnClickListener(this);
        iv_fabTool_refresh.setOnClickListener(this);
        iv_fabTool_delete.setOnClickListener(this);
        iv_fabTool_remove.setOnClickListener(this);
        iv_fabTool_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == view_fabtoolbar){
            if (isShow){
                view_fabtoolbar.hide();
                isShow = false;
            }else {
                view_fabtoolbar.show();
                isShow = true;
            }
        }else if (view == iv_fabTool_create){
            LemonBubble.showRight(RadialButtonTest.this,"创建",1000);
            view_fabtoolbar.hide();
        }else if (view == iv_fabTool_refresh){
            LemonBubble.showRight(RadialButtonTest.this,"刷新",1000);
            view_fabtoolbar.hide();
        }else if (view == iv_fabTool_delete){
            LemonBubble.showRight(RadialButtonTest.this,"删除",1000);
            view_fabtoolbar.hide();
        }else if (view == iv_fabTool_remove){
            LemonBubble.showRight(RadialButtonTest.this,"移除",1000);
            view_fabtoolbar.hide();
        }else if (view == iv_fabTool_search){
            LemonBubble.showRight(RadialButtonTest.this,"搜索",1000);
            view_fabtoolbar.hide();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
    }
}
