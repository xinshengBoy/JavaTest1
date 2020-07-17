package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;
import com.yasic.library.particletextview.MovingStrategy.BidiHorizontalStrategy;
import com.yasic.library.particletextview.MovingStrategy.BidiVerticalStrategy;
import com.yasic.library.particletextview.MovingStrategy.CornerStrategy;
import com.yasic.library.particletextview.MovingStrategy.HorizontalStrategy;
import com.yasic.library.particletextview.MovingStrategy.MovingStrategy;
import com.yasic.library.particletextview.MovingStrategy.RandomMovingStrategy;
import com.yasic.library.particletextview.MovingStrategy.VerticalStrategy;
import com.yasic.library.particletextview.Object.ParticleTextViewConfig;
import com.yasic.library.particletextview.View.ParticleTextView;

/**
 * ParticleTextView 是一个 Android 平台的自定义 view 组件，可以用彩色粒子组成指定的文字，并配合多种动画效果和配置属性，呈现出丰富的视觉效果。
 * compile 'yasic.library.ParticleTextView:particletextview:0.0.4'
 * http://p.codekk.com/detail/Android/Yasic/ParticleTextView
 * Created by admin on 2017/5/11.
 *
 * 很耗内存，手机很容易就滚烫滚烫的
 * 我使用六个按钮来实现这六种样式的动画，但是没有反应，是不是不能用按钮呢？还有一个问题就是内存问题，如果我在一个页面要同时显示这六个动画，页面会很卡很卡，有没有什么优化呢？
 */

public class ParticleTextViewTest extends Activity implements View.OnClickListener{

//    private ParticleTextSurfaceView particle1,particle2,particle3,particle4,particle5,particle6;
    private Button btn_particle_1,btn_particle_2,btn_particle_3,btn_particle_4,btn_particle_5,btn_particle_6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_particle_textview);

        initView();
    }

    private void initView(){
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "粒子文字");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        //随机移动
//        particle1 = (ParticleTextSurfaceView)findViewById(R.id.txt_particle_textview1);
////        handler.sendEmptyMessage(0);
//        //角落移动
//        particle2 = (ParticleTextSurfaceView)findViewById(R.id.txt_particle_textview2);
////        handler.sendEmptyMessage(1);
//        //左往中
//        particle3 = (ParticleTextSurfaceView)findViewById(R.id.txt_particle_textview3);
////        handler.sendEmptyMessage(2);
//        //左右往中
//        particle4 = (ParticleTextSurfaceView)findViewById(R.id.txt_particle_textview4);
////        handler.sendEmptyMessage(3);
//        //上往中
//        particle5 = (ParticleTextSurfaceView)findViewById(R.id.txt_particle_textview5);
////        handler.sendEmptyMessage(4);
//        //上下往中
//        particle6 = (ParticleTextSurfaceView)findViewById(R.id.txt_particle_textview6);
//        handler.sendEmptyMessage(5);
        btn_particle_1 = (Button)findViewById(R.id.btn_particle_1);
        btn_particle_2 = (Button)findViewById(R.id.btn_particle_2);
        btn_particle_3 = (Button)findViewById(R.id.btn_particle_3);
        btn_particle_4 = (Button)findViewById(R.id.btn_particle_4);
        btn_particle_5 = (Button)findViewById(R.id.btn_particle_5);
        btn_particle_6 = (Button)findViewById(R.id.btn_particle_6);

        btn_particle_1.setOnClickListener(this);
        btn_particle_2.setOnClickListener(this);
        btn_particle_3.setOnClickListener(this);
        btn_particle_4.setOnClickListener(this);
        btn_particle_5.setOnClickListener(this);
        btn_particle_6.setOnClickListener(this);
//        handler.sendEmptyMessage(0);
    }

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case 0:
//                    RandomMovingStrategy random = new RandomMovingStrategy();
//                    startAnimotion(5,5,0.1,"钟志华",150,2,0.1,random,particle1);
//                    break;
//                case 1:
//                    CornerStrategy corner = new CornerStrategy();
//                    startAnimotion(6,6,0.2,"钟晓凡",130,3,0.2,corner,particle2);
//                    break;
//                case 2:
//                    HorizontalStrategy horizontal = new HorizontalStrategy();
//                    startAnimotion(7,7,0.5,"傻白甜",155,2,0.3,horizontal,particle3);
//                    break;
//                case 3:
//                    BidiHorizontalStrategy bidH = new BidiHorizontalStrategy();
//                    startAnimotion(5,5,0.3,"小白兔",150,2,0.4,bidH,particle4);
//                    break;
//                case 4:
//                    VerticalStrategy vertical = new VerticalStrategy();
//                    startAnimotion(4,4,0.3,"大眼妹",120,2,0.2,vertical,particle5);
//                    break;
//                case 5:
//                    BidiVerticalStrategy bidV = new BidiVerticalStrategy();
//                    startAnimotion(4,4,0.4,"女神",200,1,0.1,bidV,particle6);
//                    break;
//            }
//        }
//    };
    @Override
    public void onClick(View view) {
        if (view == btn_particle_1){//随机移动
            RandomMovingStrategy random = new RandomMovingStrategy();
//            startAnimotion(5,5,0.1,"钟志华",150,2,0.1,random,particle1);
        }else if (view == btn_particle_2){//角落移动
            CornerStrategy corner = new CornerStrategy();
//            startAnimotion(6,6,0.2,"钟晓凡",130,3,0.2,corner,particle2);
        }else if (view == btn_particle_3){//左往中
            HorizontalStrategy horizontal = new HorizontalStrategy();
//            startAnimotion(7,7,0.5,"傻白甜",155,2,0.3,horizontal,particle3);
        }else if (view == btn_particle_4){//左右往中
            BidiHorizontalStrategy bidH = new BidiHorizontalStrategy();
//            startAnimotion(5,5,0.3,"小白兔",150,2,0.4,bidH,particle4);
        }else if (view == btn_particle_5){//上往中
            VerticalStrategy vertical = new VerticalStrategy();
//            startAnimotion(4,4,0.3,"大眼妹",120,2,0.2,vertical,particle5);
        }else if (view == btn_particle_6){//上下往中
            BidiVerticalStrategy bidV = new BidiVerticalStrategy();
//            startAnimotion(4,4,0.4,"女神",200,1,0.1,bidV,particle6);
        }
    }


    private void startAnimotion(int rowStep,int columnStep,double releasing,String target,int textSize,int radius,double minDistance,MovingStrategy strategy,ParticleTextView txt){
        ParticleTextViewConfig config = new ParticleTextViewConfig.Builder()
                .setRowStep(rowStep)//横向间隔
                .setColumnStep(columnStep)//纵向间隔
                .setReleasing(releasing)//粒子运行速度
                .setTargetText(target)//要显示的文字
                .setTextSize(textSize)//文字大小
                .setParticleRadius(radius)//设置粒子半径
                .setParticleColorArray(new String[]{"#FF1C8DDE", "#FF22B449", "#FFFF7634"})
                .setMiniDistance(minDistance)//设置最小判决距离
                .setMovingStrategy(strategy)//设置使用随机分布式策略
                .instance();
        //添加配置
//        txt.setConfig(config);
//        //开始动画
//        txt.startAnimation();
    }
}
