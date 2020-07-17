package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import java.util.Timer;
import java.util.TimerTask;

/**霓虹灯效果(framelayout)
 * Created by Administrator on 2017/2/28.
 */

public class FrameLight extends Activity {

    private int [] colors = new int[]{R.color.red,R.color.blue,R.color.yellow,R.color.black,R.color.pink,R.color.orange};
    private int [] names = new int[]{R.id.txt_framelight1,R.id.txt_framelight2,R.id.txt_framelight3,R.id.txt_framelight4,R.id.txt_framelight5,R.id.txt_framelight6,};
    TextView [] views = new TextView[names.length];
    private int currentColor = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_framelight);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "霓虹灯");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        for (int i =0;i<names.length;i++){
            views[i] = (TextView)findViewById(names[i]);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        },0,500);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                for (int i=0;i<names.length;i++){
                    views[i].setBackgroundResource(colors[(i + currentColor) % names.length]);
                }
                currentColor++;
            }
        }
    };
}
