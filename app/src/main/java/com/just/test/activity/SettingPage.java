package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.leon.lib.settingview.LSettingItem;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * 设置页面封装
 * https://github.com/leonHua/LSettingView
 * compile 'com.leon:lsettingviewlibrary:1.5.0'
 * Created by admin on 2017/8/2.
 */

public class SettingPage extends Activity {

    private ImageView iv_headPhoto;
    private TextView txt_setting_name;
    private LSettingItem ls_message,ls_download,ls_card,ls_shoping,ls_location,ls_setting,ls_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting_page);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "设置页面封装");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initView();
    }

    private void initView() {
        iv_headPhoto = (ImageView)findViewById(R.id.iv_headPhoto);
        txt_setting_name = (TextView)findViewById(R.id.txt_setting_name);
        ls_message = (LSettingItem)findViewById(R.id.ls_message);
        ls_download = (LSettingItem)findViewById(R.id.ls_download);
        ls_card = (LSettingItem)findViewById(R.id.ls_card);
        ls_shoping = (LSettingItem)findViewById(R.id.ls_shoping);
        ls_location = (LSettingItem)findViewById(R.id.ls_location);
        ls_setting = (LSettingItem)findViewById(R.id.ls_setting);
        ls_about = (LSettingItem)findViewById(R.id.ls_about);

        ls_message.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                LemonBubble.showRight(SettingPage.this,"我的消息",1000);
            }
        });
        ls_download.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                LemonBubble.showRight(SettingPage.this,"我的下载",1000);
            }
        });
        ls_card.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                LemonBubble.showRight(SettingPage.this,"我的卡片",1000);
            }
        });
        ls_shoping.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                if (isChecked) {
                    LemonBubble.showRight(SettingPage.this, "我的购物车已选中", 1000);
                }else {
                    LemonBubble.showRight(SettingPage.this, "我的购物车未选中", 1000);
                }
            }
        });
        ls_location.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                if (isChecked) {
                    LemonBubble.showRight(SettingPage.this, "我的位置已打开", 1000);
                }else {
                    LemonBubble.showRight(SettingPage.this, "我的位置已关闭", 1000);
                }
            }
        });
        ls_setting.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                LemonBubble.showRight(SettingPage.this,"我的设置",1000);
            }
        });
        ls_about.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                LemonBubble.showRight(SettingPage.this,"关于我们",1000);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
    }
}
