package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.bean.StudentInformation;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**intent传值详情页面
 * Created by Administrator on 2017/3/22.
 */

public class IntentTest2 extends Activity {

    private GridView gridview_intentTest;
    private Button btn_intentContentGo;
    private List<StudentInformation> mList = new ArrayList<>();
    private IntentTestAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_inent_content);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "Intent传值测试");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        final Intent intent = getIntent();
        mList = (List<StudentInformation>) intent.getSerializableExtra("data");
        gridview_intentTest = (GridView)findViewById(R.id.gridview_intentTest);
        btn_intentContentGo = (Button)findViewById(R.id.btn_intentContentGo);

        adapter = new IntentTestAdapter(getApplicationContext(),mList,R.layout.item_intenttext);
        gridview_intentTest.setAdapter(adapter);
        gridview_intentTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StudentInformation information = new StudentInformation();
                information.setName(mList.get(i).getName());
                boolean isCheck = mList.get(i).isCheck();
                if (isCheck){
                    information.setCheck(false);
                }else {
                    information.setCheck(true);
                }
                mList.set(i,information);
                adapter.onChangeBackgoundColor(mList);
                adapter.notifyDataSetChanged();
            }
        });

        btn_intentContentGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent(IntentTest2.this,IntentTest.class);
                resultIntent.putExtra("data",(Serializable) mList);
                setResult(1,resultIntent);
                finish();
            }
        });
    }

    private class IntentTestAdapter extends CommonAdapter<StudentInformation> {

        public IntentTestAdapter(Context context, List<StudentInformation> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, StudentInformation item) {
            TextView txt_intentcontent = helper.getView(R.id.txt_intentcontent);
            boolean isCheck = item.isCheck();
            if (isCheck){
                txt_intentcontent.setBackgroundColor(Color.GREEN);
            }else {
                txt_intentcontent.setBackgroundColor(Color.WHITE);
            }
            txt_intentcontent.setText(item.getName());

        }

        public void onChangeBackgoundColor(List<StudentInformation> data){
            mDatas = data;
        }
    }
}
