package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**网格布局
 * Created by Administrator on 2017/3/1.
 */

public class GridLayoutTest extends Activity {

    private GridLayout root;
    private TextView txt_gridLayout;
    private Button btn_gridLayouts;
    private String [] chars = new String[]{"7","8","9","÷",
                                            "4","5","6","×",
                                            "1","2","3","-",
                                            ".","0","=","+"};
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gridlayout_test);

//        //// TODO: 2016/12/21 actionbar
//        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
//        Bundle bundle = new Bundle();
//        bundle.putBoolean("back", true);
//        bundle.putString("leftText", null);
//        bundle.putString("title", "网格布局");
//        bundle.putBoolean("rightImage", false);
//        bundle.putString("rightText", null);
//        MyActionBar.actionbar(this,headerLayout,bundle);

        root = (GridLayout) findViewById(R.id.root);
        txt_gridLayout = (TextView)findViewById(R.id.txt_gridLayout);
        btn_gridLayouts = (Button)findViewById(R.id.btn_gridLayouts);
        for (int i=0;i<chars.length;i++){
            final Button button = new Button(this);
            button.setText(chars[i]);
            button.setTextSize(40);
            //制定该组件所在的行
            GridLayout.Spec rowSpec = GridLayout.spec(i / 4 + 2);
            GridLayout.Spec columeSpec = GridLayout.spec(i % 4);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec,columeSpec);
            params.setGravity(Gravity.FILL);
            root.addView(button,params);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    result += button.getText().toString();
                    txt_gridLayout.setText(result);
                }
            });
        }

        btn_gridLayouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = "";
                txt_gridLayout.setText(result);
            }
        });
    }
}
