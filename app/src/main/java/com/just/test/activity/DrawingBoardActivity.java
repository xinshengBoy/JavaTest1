package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.DrawingBoard;
import com.just.test.widget.MyActionBar;

/**画板
 * Created by Administrator on 2017/3/15.
 */

public class DrawingBoardActivity extends Activity {

    private DrawingBoard db_drawing;
    private Button btn_drawing_reset;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawingboard);
        context = this;

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "画板");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        db_drawing = (DrawingBoard)findViewById(R.id.db_drawing);
        btn_drawing_reset = (Button)findViewById(R.id.btn_drawing_reset);
        btn_drawing_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_drawing.reSet();
            }
        });
    }
}
