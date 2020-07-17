package com.just.test.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.bean.InternationalLanguage;
import com.just.test.bean.MessageEvent;
import com.just.test.listview.MyScrollListView;
import com.just.test.tools.CommonAdapter;
import com.just.test.widget.MyActionBar;
import com.king.base.BaseActivity;
import com.king.base.adapter.ViewHolderAdapter;
import com.king.base.adapter.holder.ViewHolder;
import com.king.base.model.EventMessage;
import com.luoshihai.xxdialog.DialogViewHolder;
import com.luoshihai.xxdialog.XXDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 杂七杂八
 * android开发基础类
 * compile 'com.king.base:base:2.0.1'
 * https://github.com/jenly1314/Base
 * Created by admin on 2017/5/9.
 * Base是针对于Android开发封装好一些常用的基类，主要包括通用的Adapter、Activity、Fragment、Dialog等、和一些常用的Util类，只为更简单。
 *
 * xxdialog
 * https://github.com/luoshihai/XXDialog
 */

public class ZaqizabaTest extends BaseActivity {

    private MyScrollListView listview_countryLanguage,listview_zaqizaba;
    private List<String> mList = new ArrayList<>();
    private List<InternationalLanguage> interList = new ArrayList<>();
    private XXDialog dialog;
    @Override
    public void initUI() {
        setContentView(R.layout.layout_zaqizaba);
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "杂七杂八");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        listview_countryLanguage = (MyScrollListView) findViewById(R.id.listview_countryLanguage);
        listview_zaqizaba = (MyScrollListView) findViewById(R.id.listview_zaqizaba);

        //注册事件
//        EventBus.getDefault().register(ZaqizabaTest.this);
    }

    @Override
    public void initData() {
        /**java所支持的国家的语言**/
        Locale [] localeList = Locale.getAvailableLocales();
        for (int i=0;i<localeList.length;i++){
            if (localeList[i].getDisplayCountry() != null && !localeList[i].getDisplayCountry().equals("")) {
                InternationalLanguage inter = new InternationalLanguage();
                inter.setDisplayCountry(localeList[i].getDisplayCountry());
                inter.setCountry(localeList[i].getCountry());
                inter.setDisplayLanguage(localeList[i].getDisplayLanguage());
                inter.setLanguage(localeList[i].getLanguage());

                interList.add(inter);
            }
        }
        EventBus.getDefault().post(new MessageEvent(1,"读取完成"));

        for (int i=1;i<55;i++){
            mList.add("人民的名义网络版第"+i+"集");
        }
        ZaqizabaAdapter adapter = new ZaqizabaAdapter(ZaqizabaTest.this,mList);
        listview_zaqizaba.setAdapter(adapter);
        listview_zaqizaba.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                dialog = new XXDialog(ZaqizabaTest.this,R.layout.layout_xxdialog){
                    @Override
                    public void convert(DialogViewHolder holder) {
                        Button cancel = holder.getView(R.id.btn_xxdialog_cancel);
                        Button ok = holder.getView(R.id.btn_xxdialog_ok);
                        holder.setText(R.id.txt_xxdialog,mList.get(i));

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                    }
                };
                dialog.fromBottomToMiddle().backgroundLight(0.5).fullWidth().showDialog(true);
            }
        });
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void onEventMessage(EventMessage em) {

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMoonEvent(MessageEvent event){
        int code = event.getCode();
        if (code == 1){
            InternationalLanguageAdapter interAdapter = new InternationalLanguageAdapter(ZaqizabaTest.this,interList,R.layout.item_bluetooth);
            listview_countryLanguage.setAdapter(interAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(ZaqizabaTest.this);
    }

    private class ZaqizabaAdapter extends ViewHolderAdapter<String>{

        public ZaqizabaAdapter(Context context, List<String> listData) {
            super(context, listData);
        }

        @Override
        public View buildConvertView(LayoutInflater layoutInflater, String s, int position) {
            View view = layoutInflater.inflate(R.layout.item_intenttext,null);

            return view;
        }

        @Override
        public void bindViewDatas(ViewHolder holder, String s, int position) {
            TextView txt_intentcontent = holder.getView(R.id.txt_intentcontent);
            txt_intentcontent.setText(s);
        }
    }

    private class InternationalLanguageAdapter extends CommonAdapter<InternationalLanguage>{

        public InternationalLanguageAdapter(Context context, List<InternationalLanguage> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(com.just.test.tools.ViewHolder helper, InternationalLanguage item) {
            TextView displayCountry = helper.getView(R.id.item_bluetooth_name);
            TextView country = helper.getView(R.id.item_bluetooth_address);
            TextView displayLanguage = helper.getView(R.id.item_bluetooth_state);
            TextView language = helper.getView(R.id.item_bluetooth_type);

            displayCountry.setText(item.getDisplayCountry());
            country.setText(item.getCountry());
            displayLanguage.setText(item.getDisplayLanguage());
            language.setText(item.getLanguage());
        }
    }
}
