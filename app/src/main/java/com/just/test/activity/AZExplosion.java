package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.azexplosion.ExplosionField;
import com.just.test.widget.MyActionBar;

/**
 * 粒子爆破效果
 * https://github.com/Xieyupeng520/AZExplosion
 * Created by admin on 2017/5/12.
 */

public class AZExplosion extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_azexplosion);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "粒子爆破效果");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        ExplosionField field = new ExplosionField(AZExplosion.this);
        field.addListener(findViewById(R.id.azexplosion_root));

    }
}
