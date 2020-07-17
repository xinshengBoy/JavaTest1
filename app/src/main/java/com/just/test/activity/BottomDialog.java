package com.just.test.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.fragment.FindFragment;
import com.just.test.fragment.FragmentOne;
import com.just.test.fragment.FragmentTwo;
import com.just.test.fragment.FragmentZero;
import com.just.test.fragment.ScheduleFragment;
import com.just.test.fragment.SettingFragment;
import com.just.test.viewpager.CustomViewPager;
import com.just.test.widget.MyActionBar;
import com.rey.material.app.BottomSheetDialog;
import com.rey.material.app.SimpleDialog;
import com.rey.material.widget.TabIndicatorView;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.steamcrafted.loadtoast.LoadToast;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 底部弹出对话框
 * 参考网址：http://blog.csdn.net/zouzhigang96/article/details/50454111
 * Created by admin on 2017/6/14.
 */

public class BottomDialog extends AppCompatActivity implements View.OnClickListener{

    private Dialog dialog;
    private TabIndicatorView indicator_material;
    private Tab [] mItems = new Tab[]{Tab.HOME,Tab.FIND, Tab.CHAT,Tab.FRIEND,Tab.VIDEO, Tab.SETTING};
    private CustomViewPager view_customview;
    private BottomSheetDialog dialog1;
    private Button btn_bottomDialog_start3,btn_bottomDialog_start4;
    private LoadToast it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bottom_dialog);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 底部弹出对话框");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        //// TODO: 2017/6/15 https://github.com/code-mc/loadtoast   compile 'net.steamcrafted:load-toast:1.0.10'
        it = new LoadToast(BottomDialog.this);
        indicator_material = (TabIndicatorView)findViewById(R.id.indicator_material);
        view_customview = (CustomViewPager)findViewById(R.id.view_customview);

        MaterialPagerAdapter adapter = new MaterialPagerAdapter(getSupportFragmentManager(),mItems);
        view_customview.setAdapter(adapter);
        indicator_material.setTabIndicatorFactory(new TabIndicatorView.ViewPagerIndicatorFactory(view_customview));
        view_customview.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        view_customview.setCurrentItem(0);

        btn_bottomDialog_start3 = (Button)findViewById(R.id.btn_bottomDialog_start3);
        btn_bottomDialog_start4 = (Button)findViewById(R.id.btn_bottomDialog_start4);

        btn_bottomDialog_start3.setOnClickListener(this);
        btn_bottomDialog_start4.setOnClickListener(this);
    }

    public void onBottomDialogClick(View view){
        dialog = new Dialog(BottomDialog.this,R.style.my_bottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(BottomDialog.this).inflate(R.layout.item_buttom_dialog,null);
        root.findViewById(R.id.btn_bottomDialog_photo).setOnClickListener(this);
        root.findViewById(R.id.btn_bottomDialog_picture).setOnClickListener(this);
        root.findViewById(R.id.btn_bottomDialog_cancel).setOnClickListener(this);
        dialog.setContentView(root);//添加视图
        Window window = dialog.getWindow();//获取窗体对象
        window.setGravity(Gravity.BOTTOM);//显示的位置
        window.setWindowAnimations(R.style.bottomStyle);//添加动画
        WindowManager.LayoutParams params = window.getAttributes();//获取对话框当前的参数值
        params.x = 0;//新位置X坐标
        params.y = -20;//新位置Y坐标
        params.width = getResources().getDisplayMetrics().widthPixels;//长度
        root.measure(0,0);
        params.height = root.getMeasuredHeight();//高度
        params.alpha = 9f;//透明度
        window.setAttributes(params);
        dialog.show();

    }

    //// TODO: 2017/6/15 开源框架弹出底部对话框
    public void onBottomDialogClick2(View view){
        LinearLayout root = (LinearLayout) LayoutInflater.from(BottomDialog.this).inflate(R.layout.item_buttom_dialog,null);
        root.findViewById(R.id.btn_bottomDialog_photo).setOnClickListener(this);
        root.findViewById(R.id.btn_bottomDialog_picture).setOnClickListener(this);
        root.findViewById(R.id.btn_bottomDialog_cancel).setOnClickListener(this);

        dialog1 = new BottomSheetDialog(BottomDialog.this);
        dialog1.setContentView(root);
        dialog1.heightParam(500).inDuration(500).outDuration(500).inInterpolator(new BounceInterpolator())
                .outInterpolator(new AnticipateInterpolator()).cancelable(true).show();
    }
    @Override
    public void onClick(View view) {
        com.rey.material.app.Dialog.Builder builder = null;
        switch (view.getId()){
            case R.id.btn_bottomDialog_photo:
                LemonBubble.showRight(BottomDialog.this,"正在打开相机",1000);
                if (dialog != null){
                    dialog.dismiss();
                }
                if (dialog1 != null){
                    dialog1.dismiss();
                }
                break;
            case R.id.btn_bottomDialog_picture:
                LemonBubble.showRight(BottomDialog.this,"正在打开相册",1000);
                if (dialog != null){
                    dialog.dismiss();
                }
                if (dialog1 != null){
                    dialog1.dismiss();
                }
                break;
            case R.id.btn_bottomDialog_cancel:
                if (dialog != null){
                    dialog.dismiss();
                }
                if (dialog1 != null){
                    dialog1.dismiss();
                }
                break;
            case R.id.btn_bottomDialog_start3:
                it.setText("sending reply...").setTextColor(Color.WHITE).setBackgroundColor(Color.BLACK).success();
                builder = new SimpleDialog.Builder(R.style.Material_App_Dialog_DatePicker_Light);
                builder.title("通知")
                        .positiveAction("已读")
                        .negativeAction("删除");
                break;
            case R.id.btn_bottomDialog_start4:
                it.setText("数据获取中").setBackgroundColor(Color.GREEN).setTextColor(Color.WHITE).show();
                builder = new SimpleDialog.Builder(R.style.Material_App_Dialog_DatePicker_Light);
                ((SimpleDialog.Builder)builder).message("今天可以早点下班")
                        .positiveAction("知道了")
                        .negativeAction("还要加会班");
                break;
        }
    }

    public enum Tab{
        HOME ("主页"),
        FIND ("发现"),
        SETTING ("设置"),
        VIDEO ("视频"),
        FRIEND ("朋友"),
        CHAT ("聊天");

        private final String name;
        private Tab(String s){
            name = s;
        }

        public boolean equalsName(String otherName){
            return (otherName != null) && name.equals(otherName);
        }

        public String toString(){
            return name;
        }
    }

    private static class MaterialPagerAdapter extends FragmentStatePagerAdapter{

        private final Tab[] mTabs;
        private final Fragment [] mFragment;
        private static final Field sActiveField;
        static {
            Field f = null;
            try {
                Class<?> c = Class.forName("android.support.v4.app.FragmentManagerImpl");
                f = c.getDeclaredField("mActive");//这里面的命名要和下面fragment列表的命名一致
                f.setAccessible(true);
            } catch (Exception e){
                e.printStackTrace();
            }
            sActiveField = f;
        }

        private MaterialPagerAdapter(FragmentManager fm, Tab [] tabs) {
            super(fm);
            mTabs = tabs;
            mFragment = new Fragment[mTabs.length];

            try {
                ArrayList<Fragment> mActive = (ArrayList<Fragment>) sActiveField.get(fm);
                if (mActive != null){
                    for (Fragment fragment : mActive){
                        if (fragment instanceof FindFragment){
                            setFragment(Tab.FIND,fragment);
                        }else if (fragment instanceof FragmentOne){
                            setFragment(Tab.HOME,fragment);
                        }else if (fragment instanceof FragmentTwo){
                            setFragment(Tab.CHAT,fragment);
                        }else if (fragment instanceof FragmentZero){
                            setFragment(Tab.FRIEND,fragment);
                        }else if (fragment instanceof SettingFragment){
                            setFragment(Tab.SETTING,fragment);
                        }else if (fragment instanceof ScheduleFragment){
                            setFragment(Tab.VIDEO,fragment);
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        private void setFragment(Tab tab,Fragment f){
            for (int i=0;i<mTabs.length;i++){
                if (mTabs[i] == tab){
                    mFragment[i] = f;
                    break;
                }
            }
        }

        @Override
        public Fragment getItem(int position) {
            if (mFragment[position] == null){
                switch (mTabs[position]){
                    case FIND:
                        mFragment[position] = FindFragment.newInstance();
                        break;
                    case HOME:
                        mFragment[position] = FragmentOne.newInstance();
                        break;
                    case CHAT:
                        mFragment[position] = FragmentTwo.newInstance();
                        break;
                    case FRIEND:
                        mFragment[position] = FragmentZero.newInstance();
                        break;
                    case SETTING:
                        mFragment[position] = SettingFragment.newInstance();
                        break;
                    case VIDEO:
                        mFragment[position] = ScheduleFragment.newInstance();
                        break;
                }
            }
            return mFragment[position];
        }

        @Override
        public int getCount() {
            return mFragment.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs[position].toString().toUpperCase();
        }
    }
}
