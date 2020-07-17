package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.FlyTxtView;
import com.just.test.widget.MyActionBar;

/**
 * 自动补全邮箱
 * https://github.com/g707175425/CloudEditText
 * Created by admin on 2017/6/12.
 */

public class AutoFinishEmail extends Activity {

    private FlyTxtView view_flyTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_autofinish_email);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "自动补全邮箱");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

//        CloudEditTextImp et_autoFinishEmail = (CloudEditTextImp) findViewById(R.id.et_autoFinishEmail);
//        et_autoFinishEmail.addSpan(c);

        view_flyTextview = (FlyTxtView)findViewById(R.id.view_flyTextview);
        view_flyTextview.setTextSize(26);
        view_flyTextview.setTextColor(Color.GREEN);
        view_flyTextview.setTexts("青花瓷\n" +
                "\n" +
                "素胚勾勒出青花笔锋浓转淡\n" +
                "瓶身描绘的牡丹一如你初妆\n" +
                "冉冉檀香透过窗心事我了然\n" +
                "宣纸上走笔至此搁一半\n" +
                "釉色渲染仕女图韵味被私藏\n" +
                "而你嫣然的一笑如含苞待放\n" +
                "你的美一缕飘散\n" +
                "去到我去不了的地方\n" +
                "\n" +
                "天青色等烟雨\n" +
                "而我在等你\n" +
                "炊烟袅袅升起\n" +
                "隔江千万里\n" +
                "在瓶底书汉隶仿前朝的飘逸\n" +
                "就当我为遇见你伏笔\n" +
                "天青色等烟雨\n" +
                "而我在等你\n" +
                "月色被打捞起\n" +
                "晕开了结局\n" +
                "如传世的青花瓷自顾自美丽\n" +
                "你眼带笑意\n" +
                "色白花青的锦鲤跃然於碗底\n" +
                "临摹宋体落款时却惦记着你\n" +
                "你隐藏在窑烧里千年的秘密\n" +
                "极细腻犹如绣花针落地\n" +
                "帘外芭蕉惹骤雨\n" +
                "门环惹铜绿\n" +
                "而我路过那江南小镇惹了你\n" +
                "在泼墨山水画里\n" +
                "你从墨色深处被隐去");
        view_flyTextview.startAnimation();

        Button btn_flytextview_change = (Button) findViewById(R.id.btn_flytextview_change);
        btn_flytextview_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_flyTextview.setTexts("跟着我 左手 右手 一个慢动作 右手 左手 慢动作重播 玺：这首歌 给你快乐 你有没有爱上我 源： 跟着我 鼻子 眼睛 动一动耳朵 装乖 耍帅 换不停风格 合：青春有太多 未知的猜测 成长的烦恼算什么 源：皮鞋擦亮 换上西装 佩戴上一克拉的梦想 玺：我的勇敢充满电量 昂首到达每一个地方 凯： 这世界 的太阳 因为自信才能把我照亮 这舞台 的中央 合：有我才闪亮 有我才能发着光 合： 跟着我 左手 右手 一个慢动作 右手 左手慢动作重播 这首歌 给你快乐 你有没有爱上我 跟着我 鼻子眼睛 动一动耳朵 青春有太多 未知的猜测 成长的烦恼算什么 源：经常会想 长大多好 有些事情却只能想象 玺：想说就说 想做就做 为了明天的自己鼓掌 凯：这世界 的太阳 因为自信才能把我照亮 这舞台 的中央 合：有我才闪亮 有我才能发着光 合： 跟着我左手右手 一个慢动作 右手 左手 慢动作重播 这首歌 给你快乐 动一动耳朵 装乖 耍帅 换不停风格 青春有太多 未知的猜测 成长的烦恼算什么 凯Rap: 向明天 对不起 向前冲 不客气 一路有你 充满斗志无限动力 合：男子汉 没有什么输不起 正太修炼成功的秘籍 源： 跟着我左手右手 一个慢动作 玺： 右手左手慢动作重播 凯： 这首歌 给你快乐 合： 跟着我鼻子眼睛 动一动耳朵 青春有太多 未知的猜测 成长的烦恼算什么");
                view_flyTextview.startAnimation();
            }
        });
    }
}
