package com.just.test.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * 图片长按保存
 * Created by Administrator on 2017/2/18.
 */

public class LongClickSavePiture extends Activity {

    private ImageView iv_longClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_longclicksavepicture);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "图片长按保存");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        iv_longClick = (ImageView)findViewById(R.id.iv_longClick);
        iv_longClick.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LongClickSavePiture.this);
                builder.setMessage("保存图片");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        handler.sendEmptyMessage(0);
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                savePicture();
                Toast.makeText(LongClickSavePiture.this,"保存成功",Toast.LENGTH_LONG).show();
            }
        }
    };
    private void savePicture() {
        try {
            String sdcard = Environment.getExternalStorageDirectory().toString();
            File file = new File(sdcard+"/zzh/");
            if (!file.exists()){
                file.mkdirs();
            }

            File imageFile = new File(file.getAbsolutePath(),new Date().getTime()+".jpg");
            FileOutputStream os = new FileOutputStream(imageFile);
            iv_longClick.setDrawingCacheEnabled(true);//设置允许获取图片位图
            Bitmap bitmap = iv_longClick.getDrawingCache();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
