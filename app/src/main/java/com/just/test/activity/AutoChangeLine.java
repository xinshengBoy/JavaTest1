package com.just.test.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 输入的文字自动换行
 * 并将输入的文字转换为图片
 * Created by admin on 2017/4/28.
 */

public class AutoChangeLine extends Activity {

    private EditText et_autochangeline;
    private ImageView iv_autoChangeLine;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_autochangeline);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "自动换行");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        et_autochangeline = (EditText)findViewById(R.id.et_autochangeline);
        iv_autoChangeLine = (ImageView)findViewById(R.id.iv_autoChangeLine);
        Button btn_autoChange = (Button) findViewById(R.id.btn_autoChange);
        btn_autoChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = et_autochangeline.getText().toString();
                if (input == null || input.equals("")){
                    Toast.makeText(AutoChangeLine.this,"请输入内容",Toast.LENGTH_SHORT).show();
                }else {
                    bitmap = textAsBitmap(input,42,Color.RED);
                    iv_autoChangeLine.setVisibility(View.VISIBLE);
                    iv_autoChangeLine.setImageBitmap(bitmap);
                }
            }
        });

        iv_autoChangeLine.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AutoChangeLine.this)
                        .setTitle("分享")
                        .setMessage("您要分享这图片么？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                bitmap = compressImage(bitmap);
                                File file = bitmapToFile(bitmap);
                                Uri imageUri = Uri.fromFile(file);

                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.putExtra(Intent.EXTRA_STREAM,imageUri);
                                intent.setType("image/*");
                                startActivity(Intent.createChooser(intent,"share"));
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.setCancelable(false);
                builder.create().show();
                return true;
            }
        });
    }

    //// TODO: 2017/4/28 文字转图片
    private Bitmap textAsBitmap(String text, float textSize, int textColor){
        //// TODO: 2017/4/28 获取屏幕宽高
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;

        TextPaint paint = new TextPaint();

        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);

        StaticLayout layout = new StaticLayout(text,paint,screenWidth, Layout.Alignment.ALIGN_NORMAL,1.3f,0.0f,true);

        Bitmap bitmap = Bitmap.createBitmap(layout.getWidth()+20,layout.getHeight()+20, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.translate(10,10);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        layout.draw(canvas);

        return bitmap;
    }

    //视图转bitmap并做压缩处理
    private Bitmap compressImage(Bitmap bitmap){
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        //质量压缩方法。。。数值，100表示不压缩
        bitmap.compress(Bitmap.CompressFormat.JPEG,10,bao);

        int options = 100;
        while (bao.toByteArray().length / 1024 > 400){//循环判断图片是否大于400kb
            bao.reset();//重置
            bitmap.compress(Bitmap.CompressFormat.JPEG,options,bao);
            options -= 10;//每次减少10
        }
        //将压缩后的数据放里面
        ByteArrayInputStream bin = new ByteArrayInputStream(bao.toByteArray());
        //重新生成图片
        Bitmap newBitmap = BitmapFactory.decodeStream(bin,null,null);

        return newBitmap;
    }

    //// TODO: 2017/4/28 将图片保存
    public File bitmapToFile(Bitmap bitmap){
        String path = "/sdcard/zzh/";
        File file = new File(path,"share"+".jpg");
        if (file.exists()){
            file.delete();
        }

        try {
            FileOutputStream os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,os);
            os.flush();
            os.close();
            bitmap.recycle();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return file;
        }
    }
}
