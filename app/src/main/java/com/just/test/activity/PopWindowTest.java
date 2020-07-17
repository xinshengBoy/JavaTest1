package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * popwindow
 * Created by admin on 2017/5/23.
 */

public class PopWindowTest extends Activity implements View.OnClickListener{

    private TextView txt_popwindow_test;
    private Button btn_popwindow_test;
    private ImageView iv_popwindw_test;
    private PopupWindow pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_popwindows);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "popwindow");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initView();
    }

    private void initView(){
        txt_popwindow_test = (TextView)findViewById(R.id.txt_popwindow_test);
        btn_popwindow_test = (Button)findViewById(R.id.btn_popwindow_test);
        iv_popwindw_test = (ImageView)findViewById(R.id.iv_popwindw_test);

        txt_popwindow_test.setOnClickListener(this);
        btn_popwindow_test.setOnClickListener(this);
        iv_popwindw_test.setOnClickListener(this);

        showPopWindow();
    }

    @Override
    public void onClick(View view) {
        if (view == txt_popwindow_test){
            pop.showAsDropDown(txt_popwindow_test);
            setBackgroundAlpha(PopWindowTest.this,0.5f);
        }else if (view == btn_popwindow_test){
            pop.showAsDropDown(btn_popwindow_test);
            setBackgroundAlpha(PopWindowTest.this,0.5f);
        }else if (view == iv_popwindw_test){
            pop.showAsDropDown(iv_popwindw_test);
            setBackgroundAlpha(PopWindowTest.this,0.5f);
        }else if (view.getId() == R.id.pop_layout_home){
            LemonBubble.showRight(this,"首页",3000);
            pop.dismiss();
            setBackgroundAlpha(PopWindowTest.this,1f);
        }else if (view.getId() == R.id.pop_layout_find) {
            LemonBubble.showRight(this,"发现",3000);
            pop.dismiss();
            setBackgroundAlpha(PopWindowTest.this,1f);
        }else if (view.getId() == R.id.pop_layout_setting){
            LemonBubble.showRight(this,"设置",3000);
            pop.dismiss();
            setBackgroundAlpha(PopWindowTest.this,1f);
        }
    }

    /**
     * 显示pop弹窗
     */
    private void showPopWindow(){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_popwindows,null);
        pop = new PopupWindow(view);

        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = manager.getDefaultDisplay().getWidth();
        pop.setWidth(width / 3);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.update();

        view.findViewById(R.id.pop_layout_home).setOnClickListener(this);
        view.findViewById(R.id.pop_layout_find).setOnClickListener(this);
        view.findViewById(R.id.pop_layout_setting).setOnClickListener(this);
    }

    /**
     * 设置页面的透明度
     * @param bgAlpha 1表示不透明 0表示完全透明
     */
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        pop.dismiss();
        setBackgroundAlpha(PopWindowTest.this,1f);
    }
}
