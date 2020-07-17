package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.test.R;

/**
 * 动态生成多个图片控件
 * Created by user on 2016/12/8.
 */

public class CreateManyImageWdiget extends Activity implements View.OnClickListener{

    private EditText hang,lie;
    private Button create,clear;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_many_imagewdiget);

        hang = (EditText)findViewById(R.id.et_create_imagewdiget_hang);
        lie = (EditText)findViewById(R.id.et_create_imagewdiget_lie);
        create = (Button)findViewById(R.id.btn_create_image);
        clear = (Button)findViewById(R.id.btn_clear_image);
        layout = (LinearLayout)findViewById(R.id.layout_imageWdiget);
        create.setOnClickListener(this);
        clear.setOnClickListener(this);

        showSoftInput(hang);
    }

    @Override
    public void onClick(View view) {
        if (view == create){
            doClick();
        }else if (view == clear){
            clearClick();
        }
    }

    public void doClick() {
        layout.removeAllViews();
        try {
            int rows=Integer.valueOf(hang.getText().toString().trim());
            int columns=Integer.valueOf(lie.getText().toString().trim());
            //控制数量，不然会过多的消耗内存
            if (rows <= 50 && columns <= 50) {
                createImage(rows, columns, 10);
            }else {
                Toast.makeText(this,"要求太高了吧",Toast.LENGTH_LONG).show();
            }
            hideSoftInput();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    //清除图片
    public void clearClick() {
        hideSoftInput();
        layout.removeAllViews();
    }
    //生成图片
    public void createImage(int rows, int columns,int margin){
        //params用户控制Imageview的大小
        int width=initWandH(columns,margin);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                width,width);
        for(int i=0;i<rows;i++){
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int j=0;j<columns;j++){
                //根据不同位置，控制不同margin
                params.setMargins(10, 10, 10, 10);
                if(j==0){
                    params.setMargins(10, 10, 0, 0);
                }else if(j==columns-1){
                    params.setMargins(0, 10, 10, 0);
                }
                ImageView iv=new ImageView(CreateManyImageWdiget.this);
                iv.setImageResource(R.drawable.icon_app);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                linearLayout.addView(iv,params);
            }
            layout.addView(linearLayout);
        }
    }

    //计算控件的宽度
    public int initWandH(int columnNum,int margin){
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        int ivWidth=screenWidth-(columnNum+1)*margin;
        return ivWidth/columnNum;
    }
    //隐藏软键盘
    private void hideSoftInput() {
        InputMethodManager im = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (this.getCurrentFocus() != null) {
            im.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    //弹出软键盘
    private void showSoftInput(EditText et) {
        et.setFocusableInTouchMode(true);
        et.requestFocus();
        InputMethodManager imm=(InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, 0);
    }
}
