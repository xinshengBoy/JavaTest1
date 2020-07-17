package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * TextDrawable
 https://github.com/amulyakhare/TextDrawable
 repositories{
 maven {
 url 'http://dl.bintray.com/amulyakhare/maven'
 }
 }

 dependencies {
 compile 'com.amulyakhare:com.amulyakhare.textdrawable:1.0.1'
 }
 * Created by admin on 2017/4/1.
 */

public class TextDrawableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_textdrawable);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "文字头像");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        //1. Create simple tile:矩形
        ImageView iv_textdrawable1 = (ImageView)findViewById(R.id.iv_textdrawable1);
        TextDrawable drawable1 = TextDrawable.builder().buildRect("A", Color.RED);
        iv_textdrawable1.setImageDrawable(drawable1);
        //2. Create rounded corner or circular tiles:
        ImageView iv_textdrawable2 = (ImageView)findViewById(R.id.iv_textdrawable2);
        TextDrawable drawable2 = TextDrawable.builder().buildRound("Z", Color.GREEN);
        iv_textdrawable2.setImageDrawable(drawable2);
        //圆角矩形
        ImageView iv_textdrawable3 = (ImageView)findViewById(R.id.iv_textdrawable3);
        TextDrawable drawable3 = TextDrawable.builder().buildRoundRect("Q", Color.YELLOW,30);
        iv_textdrawable3.setImageDrawable(drawable3);
        //3. Add border:
        ImageView iv_textdrawable4 = (ImageView)findViewById(R.id.iv_textdrawable4);
        TextDrawable drawable4 = TextDrawable.builder().beginConfig().withBorder(5).endConfig().buildRoundRect("G", Color.BLUE,30);
        iv_textdrawable4.setImageDrawable(drawable4);
        //4. Modify font style:定义内部字母的样式
        ImageView iv_textdrawable5 = (ImageView)findViewById(R.id.iv_textdrawable5);
        TextDrawable drawable5 = TextDrawable.builder().beginConfig().textColor(Color.GRAY).useFont(Typeface.DEFAULT).fontSize(30).bold().
                toUpperCase().endConfig().buildRoundRect("D", Color.MAGENTA,30);
        iv_textdrawable5.setImageDrawable(drawable5);
        //5. Built-in color generator:
        ImageView iv_textdrawable6 = (ImageView)findViewById(R.id.iv_textdrawable6);
        ImageView iv_textdrawable7 = (ImageView)findViewById(R.id.iv_textdrawable7);
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color1 = generator.getRandomColor();
        int color2 = generator.getColor("user@gmail.com");
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .withBorder(10)
                .endConfig()
                .rect();
        TextDrawable ic1 = builder.build("E",color1);
        TextDrawable ic2 = builder.build("F",color2);
        iv_textdrawable6.setImageDrawable(ic1);
        iv_textdrawable7.setImageDrawable(ic2);
        //6. Specify the width / height:设置长宽
        ImageView iv_textdrawable8 = (ImageView)findViewById(R.id.iv_textdrawable8);
        TextDrawable drawable8 = TextDrawable.builder()
                .beginConfig()
                .width(260)
                .height(260)
                .endConfig()
                .buildRound("H",Color.BLUE);
        iv_textdrawable8.setImageDrawable(drawable8);
        //适用于其他视图，用于背景
        TextView iv_textdrawable9 = (TextView)findViewById(R.id.iv_textdrawable9);
        TextDrawable drawable9 = TextDrawable.builder()
                .beginConfig()
                .width(360)
                .height(360)
                .endConfig()
                .buildRect("J",Color.RED);
        iv_textdrawable9.setBackground(drawable9);
    }
}
