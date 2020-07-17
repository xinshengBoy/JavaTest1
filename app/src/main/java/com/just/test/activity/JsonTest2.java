package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.just.test.R;
import com.just.test.bean.Clubs;
import com.just.test.bean.Rounds;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.FileHelper;
import com.just.test.tools.ViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/12/26.
 */

public class JsonTest2 extends Activity implements View.OnClickListener{

    private ListView listview_jsonTest2,listview_jsonTest3;
    private TextView txt_jsonTest2_title,txt_jsonTest_1,txt_jsonTest_2;
    private List<Bundle> mList = new ArrayList<>();
    private List<Rounds> mList2 = new ArrayList<>();
    private List<Bundle> mList3 = new ArrayList<>();
    private String KEY = "key";
    private String NAME = "name";
    private String CODE = "code";
    private String DATE = "date";
    private String SCORE1 = "score1";
    private String SCORE2 = "score2";
    private String title,title2;
    private Clubs clubs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_jsontest2);

        txt_jsonTest_1 = (TextView)findViewById(R.id.txt_jsonTest_1);
        txt_jsonTest_2 = (TextView)findViewById(R.id.txt_jsonTest_2);
        txt_jsonTest2_title = (TextView)findViewById(R.id.txt_jsonTest2_title);

        txt_jsonTest_1.setOnClickListener(this);
        txt_jsonTest_2.setOnClickListener(this);


        initView1();
        initView2();

    }

    private void initView1(){
        listview_jsonTest2 = (ListView) findViewById(R.id.listview_jsonTest2);

//        String source1 = FileHelper.getFromAssets(JsonTest2.this,"email4.xlsx");
        String source = FileHelper.getFromAssets(JsonTest2.this,"clubs.json");
        initData(source);
        txt_jsonTest2_title.setText(title);
        JsonTest2Adapter adapter = new JsonTest2Adapter(JsonTest2.this,mList,R.layout.jsontest2_item);
        listview_jsonTest2.setAdapter(adapter);
    }
    private void  initView2(){
        listview_jsonTest3 = (ListView) findViewById(R.id.listview_jsonTest3);
        String source2 = FileHelper.getFromAssets(JsonTest2.this,"clubs2.json");
        initData2(source2);
        txt_jsonTest2_title.setText(title2);
        JsonTest3Adapter adapter = new JsonTest3Adapter(JsonTest2.this, mList2,R.layout.jsontest3_item);
        listview_jsonTest3.setAdapter(adapter);
        listview_jsonTest3.setVisibility(View.GONE);
    }
    private void initData(String source){
        try {
            JSONObject object = new JSONObject(source);
            title = object.getString("name");
            JSONArray array = object.getJSONArray("clubs");
            for (int i=0;i<array.length();i++){
                JSONObject object1 = array.getJSONObject(i);
                Bundle bundle = new Bundle();
                bundle.putString(KEY,object1.getString(KEY));
                bundle.putString(NAME,object1.getString(NAME));
                bundle.putString(CODE,object1.getString(CODE));
                mList.add(bundle);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initData2(String source){
        try {
            JSONObject object = new JSONObject(source);
            title2 = object.getString("name");
            JSONArray array = object.getJSONArray("rounds");
            Gson gson = new Gson();
            mList2 = gson.fromJson(array.toString(),new TypeToken<List<Rounds>>(){}.getType());

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        try {
//            JSONObject object = new JSONObject(source);
//            title2 = object.getString("name");
//            JSONArray array = object.getJSONArray("rounds");
//            for (int i=0;i<array.length();i++){
//                JSONObject object1 = array.getJSONObject(i);
//                Bundle bundle = new Bundle();
//                bundle.putString(NAME,object1.getString(NAME));
//
//                JSONArray array1 = object1.getJSONArray("matches");
//                for (int j=0;j<array1.length();j++){
//                    JSONObject object2 = array.getJSONObject(j);
//                    Bundle bundle1 = new Bundle();
//                    bundle1.putString(DATE,object2.getString(DATE));
//                    bundle1.putString(SCORE1,object2.getString(SCORE1));
//                    bundle1.putString(SCORE2,object2.getString(SCORE2));
//                    JSONArray array2 = object2.getJSONArray("team"+(j+i));
//                    for (int k=0;k<array2.length();k++){
//                        JSONObject object3 = array2.getJSONObject(k);
//                        Bundle bundle2 = new Bundle();
//                        bundle2.putString(KEY,object3.getString(KEY));
//                        bundle2.putString(NAME,object3.getString(NAME));
//                        bundle2.putString(CODE,object3.getString(CODE));
//                        mList3.add(bundle2);
//                    }
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onClick(View view) {
        if (view == txt_jsonTest_1){
            listview_jsonTest2.setVisibility(View.VISIBLE);
            listview_jsonTest3.setVisibility(View.GONE);
        }else if (view == txt_jsonTest_2){
            listview_jsonTest3.setVisibility(View.VISIBLE);
            listview_jsonTest2.setVisibility(View.GONE);
        }
    }

    private class JsonTest2Adapter extends CommonAdapter<Bundle>{

        public JsonTest2Adapter(Context context, List<Bundle> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, Bundle item) {
            TextView name = helper.getView(R.id.txt_jsonTest2_name);
            TextView code = helper.getView(R.id.txt_jsonTest2_code);

            name.setText(item.getString(NAME));
            code.setText(item.getString(CODE));
        }
    }

    private class JsonTest3Adapter extends CommonAdapter<Rounds>{

        public JsonTest3Adapter(Context context, List<Rounds> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, Rounds item) {
            TextView runde = helper.getView(R.id.txt_jsontest3_item_runde);
            TextView date = helper.getView(R.id.txt_jsontest3_item_date);
            TextView team1 = helper.getView(R.id.txt_jsontest3_item_team1);
            TextView score1 = helper.getView(R.id.txt_jsontest3_item_score1);
            TextView score2 = helper.getView(R.id.txt_jsontest3_item_score2);
            TextView team2 = helper.getView(R.id.txt_jsontest3_item_team2);

            runde.setText(item.name);
            date.setText(item.matches.get(0).date);
            team1.setText(item.matches.get(0).getTeam1().get(0).name);
            score1.setText(item.matches.get(0).score1+"");
            score2.setText(item.matches.get(0).score2+"");
            team2.setText(item.matches.get(0).getTeam2().get(0).name);
        }
    }
}
