package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.just.test.R;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;
/**
 * 接口测试
 * Created by admin on 2017/4/26.
 */

public class InterFaceTest extends Activity {

    private ImageView iv_interface;
    private TextView txt_interface_1;
    private String url = "http://mb.sseinfo.com/ComInfoServer/getSOAInfocontroller.do?method=optionVideo";
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_interface);

        mQueue = Volley.newRequestQueue(InterFaceTest.this);
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "接口测试");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        Button test = (Button) findViewById(R.id.btn_interface_1);
        iv_interface = (ImageView)findViewById(R.id.iv_interface);
        txt_interface_1 = (TextView)findViewById(R.id.txt_interface_1);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Favorite(url, new favoriteLister() {
                    @Override
                    public void onSuccess(String result) {
                        txt_interface_1.setText(result);
                    }
                });
            }
        });
    }

    public interface favoriteLister{
        void onSuccess(String result);
    }

    public void Favorite(String url,final favoriteLister lister){
        StringRequest request = new StringRequest(com.android.volley.Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                lister.onSuccess(s);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LemonBubble.showError(InterFaceTest.this,volleyError.getMessage().toString(),3000);
            }
        });
        mQueue.add(request);
    }
}
