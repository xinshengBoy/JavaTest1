package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * android 5.0新特性
 * https://github.com/navasmdc/MaterialDesignLibrary
 * Created by admin on 2017/5/2.
 */

public class SimpleMaterialDesign extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_material_design);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "5.0新特性");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        //snackbar
        Snackbar.make(headerLayout,"返回",Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.RED)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(SimpleMaterialDesign.this,"还不错",Toast.LENGTH_LONG).show();
                    }
                }).show();
    }
}
