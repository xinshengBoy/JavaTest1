package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时区设置
 * Created by user on 2016/12/5.
 */

public class TimeAreaSetting extends Activity implements View.OnClickListener{
    private TextView txt_timeArea_date,txt_timeArea_result,txt_timeArea_notice,txt_timeArea_city;
    private ImageView iv_timeArea_reduce,iv_timeArea_add;
    private String[] area = new String[]{"-12","-11","-10","-9","-8","-7","-6","-5","-4","-3","-2","-1","0"
                                       ,"+1","+2","+3","+4","+5","+6","+7","+8","+9","+10","+11","+12" };
    private String[] areaText = new String[]{"西十二区","西十一区","西十区","西九区","西八区","西七区","西六区","西五区","西四区","西三区","西二区","西一区","格林尼治时间"
            ,"东一区","东二区","东三区","东四区","东五区","东六区","东七区","东八区","东九区","东十区","东十一区","东十二区" };
    private String[] areaCity = new String[]{"太平洋中部",
            "太平洋中部",
            "美国檀香山",
            "美国阿拉斯加州安克雷奇",
            "美国加里福利亚州，洛杉矶湖人队所在地。加拿大温哥华",
            "美国亚利桑那州菲尼克斯，nba太阳队所在地",
            "美国芝加哥，墨西哥墨西哥城",
            "美国纽约",
            "智力圣地亚哥",
            "巴西巴西利亚，阿根廷布宜诺斯艾利斯",
            "大西洋中部",
            "非洲大陆最西边不知名的地方",
            "英国伦敦，葡萄牙里斯本",
            "西班牙马德里，法国巴黎，德国柏林，瑞士日内瓦，丹麦哥本哈根，意大利罗马，瑞典斯德哥尔摩，波兰华沙，比利时布鲁塞尔",
            "土耳其伊斯坦布尔，南非开普敦，罗马尼亚布加勒斯特，埃及开罗，希腊雅典",
            "俄罗斯莫斯科，伊朗德黑兰",
            "阿联酋阿布扎比",
            "巴基斯坦伊斯兰堡，印度新德里 ",
            "孟加拉国达卡，缅甸仰光，中国新疆自治区",
            "泰国曼谷，马来西亚吉隆坡，新加坡",
            "天朝北京，小菲马尼拉",
            "小日本东京",
            "俄罗斯海参崴，美国关岛，澳大利亚墨尔本 ",
            "所罗门群岛",
            "新西兰惠灵顿" };
    private int position = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_timearea_setting);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "时区设置");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        txt_timeArea_city = (TextView)findViewById(R.id.txt_timeArea_city);
        txt_timeArea_notice = (TextView)findViewById(R.id.txt_timeArea_notice);
        txt_timeArea_date = (TextView)findViewById(R.id.txt_timeArea_date);
        txt_timeArea_result = (TextView)findViewById(R.id.txt_timeArea_result);
        iv_timeArea_reduce = (ImageView)findViewById(R.id.iv_timeArea_reduce);
        iv_timeArea_add = (ImageView)findViewById(R.id.iv_timeArea_add);

        iv_timeArea_reduce.setOnClickListener(this);
        iv_timeArea_add.setOnClickListener(this);
        //默认显示东八区的时间
        Message message = new Message();
        message.what = 0;
        message.arg1 = position;
        handler.sendMessage(message);
    }

    @Override
    public void onClick(View view) {
        if (view == iv_timeArea_reduce){
            if (position == 0){
                position = 25;
            }
            position = position - 1;
            if (position == area.length){
                position = 0;
            }
            Message message = new Message();
            message.what = 0;
            message.arg1 = position;
            handler.sendMessage(message);
        }else  if (view == iv_timeArea_add){
            position = position + 1;
            if (position == area.length){
                position = 0;
            }
            Message message = new Message();
            message.what = 0;
            message.arg1 = position;
            handler.sendMessage(message);
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                txt_timeArea_notice.setText("当前为"+areaText[msg.arg1]);
                txt_timeArea_date.setText(setDate(area[msg.arg1]));
                txt_timeArea_result.setText(area[msg.arg1]);
                txt_timeArea_city.setText("时区代表城市："+areaCity[position]);
            }
        }
    };
    /**
     * 获取时间并设置时区
     * @return
     */
    private String setDate(String area) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //// TODO: 2016/12/5 GMT+8:东八区  GMT-6：西六区
        format.setTimeZone(TimeZone.getTimeZone("GMT"+area));
        Date date = new Date(System.currentTimeMillis());
        String currentTimeString = format.format(date);
        return currentTimeString;
    }
}
