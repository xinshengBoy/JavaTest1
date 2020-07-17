package com.just.test.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.just.test.R;
import com.just.test.bean.VideoList;
import com.just.test.tools.PicassoUtils;
import com.just.test.widget.MyActionBar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**picasso测试
 * Created by Administrator on 2017/3/2.
 */

public class PicassoTest extends Activity {

    private String videoURL = "http://mb.sseinfo.com/ComInfoServer/getSOAInfocontroller.do?method=optionVideo";
    private ListView listview_picassoTest;
    private ProgressDialog dialog;
    private RequestQueue mQueue;
    private VideoAdapter adapter;
    private List<VideoList> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_picassotest);
        mQueue = Volley.newRequestQueue(PicassoTest.this);
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "picasso测试");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        listview_picassoTest = (ListView)findViewById(R.id.listview_picassoTest);
        listview_picassoTest.setOnScrollListener(new ListScroll());//添加滚动事件监听
        dialog = new ProgressDialog(this);
        dialog.setTitle("load.....");
        requestDate(videoURL);
//        new MyTask().execute(videoURL);
    }

    /**
     * 滑动监听，在滑动listview的时候不加载
     */
    public class ListScroll implements AbsListView.OnScrollListener{

        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {
            final Picasso picasso = Picasso.with(PicassoTest.this);
            if (i == SCROLL_STATE_IDLE || i == SCROLL_STATE_TOUCH_SCROLL){
                picasso.resumeTag(PicassoTest.this);
            }else {
                picasso.pauseTag(PicassoTest.this);
            }
        }

        @Override
        public void onScroll(AbsListView absListView, int i, int i1, int i2) {

        }
    }

    private void requestDate(String url){
        dialog.show();
        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        JSONArray array;
                        try {
                            //json数组里面又包含json数组
                            JSONArray jsonArray = new JSONArray(response.toString());
                            for (int k = 0; k < jsonArray.length(); k++) {
                                JSONObject objects = jsonArray.optJSONObject(k);
                                JSONObject value = objects.optJSONObject("value");
                                array = value.optJSONArray("list");
                                for (int i = 0; i < array.length(); i++) {
                                    VideoList videoList = new VideoList();
                                    JSONObject object = array.getJSONObject(i);
                                    videoList.setCreateTime(object.getString("createTime"));
                                    videoList.setDocURL(object.getString("docURL"));
                                    videoList.setDocTitle(object.getString("docTitle"));
                                    videoList.setExtLINK(object.getString("extStarttest_link"));
                                    videoList.setExtSSE_YJCB_SFJB(object.getString("extSSE_YJCB_SFJB"));

                                    mList.add(videoList);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PicassoTest.this,"网络请求错误：" + error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        mQueue.add(request);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                adapter = new VideoAdapter(mList);
                listview_picassoTest.setAdapter(adapter);
                dialog.dismiss();
            }
        }
    };
    class VideoAdapter extends BaseAdapter{

        private List<VideoList> list;
        public VideoAdapter(List<VideoList> list){
            this.list = list;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHodler hodler = null;
            if (view == null){
                hodler = new ViewHodler();
                view = getLayoutInflater().inflate(R.layout.item_videotest,viewGroup,false);
                hodler.iv_picassoTest = (ImageView) view.findViewById(R.id.iv_picassoTest);
                hodler.txt_picassoTitle = (TextView) view.findViewById(R.id.txt_picassoTitle);
                hodler.txt_picassoTime = (TextView) view.findViewById(R.id.txt_picassoTime);

                view.setTag(hodler);
            }else {
                hodler = (ViewHodler) view.getTag();
            }

            hodler.txt_picassoTitle.setText(list.get(i).getDocTitle());
            hodler.txt_picassoTime.setText(list.get(i).getCreateTime());
//            PicassoUtils.loadImageViewHodler(PicassoTest.this,list.get(i).getExtSSE_YJCB_SFJB(),R.drawable.dk_videoloading,hodler.iv_picassoTest);
            PicassoUtils.loadImageSize(PicassoTest.this,list.get(i).getExtSSE_YJCB_SFJB(),400,300,hodler.iv_picassoTest);
//            Picasso.with(PicassoTest.this).load(list.get(i).getExtSSE_YJCB_SFJB()).into(hodler.iv_picassoTest);
            return view;
        }
    }

    private static class ViewHodler{
        ImageView iv_picassoTest;
        TextView txt_picassoTitle;
        TextView txt_picassoTime;
    }
    /**
     * 异步任务
     */
//    class MyTask extends AsyncTask<String,Void,List<VideoList>>{
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog.show();
//        }
//
//        @Override
//        protected List<VideoList> doInBackground(String... params) {
//            final List<VideoList> list = new ArrayList<>();
//            StringRequest request = new StringRequest(videoURL,
//                    new Response.Listener<String>() {
//
//                        public void onResponse(String response) {
//                            JSONArray array;
//
//                            try {
//                                //json数组里面又包含json数组
//                                JSONArray jsonArray = new JSONArray(response.toString());
//                                for (int k = 0; k < jsonArray.length(); k++) {
//                                    JSONObject objects = jsonArray.optJSONObject(k);
//                                    JSONObject value = objects.optJSONObject("value");
//                                    array = value.optJSONArray("list");
//                                    for (int i = 0; i < array.length(); i++) {
//                                        VideoList videoList = new VideoList();
//                                        JSONObject object = array.getJSONObject(i);
//                                        videoList.setCreateTime(object.getString("createTime"));
//                                        videoList.setDocURL(object.getString("docURL"));
//                                        videoList.setDocTitle(object.getString("docTitle"));
//                                        videoList.setExtLINK(object.getString("extStarttest_link"));
//                                        videoList.setExtSSE_YJCB_SFJB(object.getString("extSSE_YJCB_SFJB"));
//
//                                        list.add(videoList);
//                                    }
//
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(PicassoTest.this,"网络请求错误：" + error.toString(), Toast.LENGTH_LONG).show();
//                }
//            });
//
//            mQueue.add(request);
//            return list;
//        }
//
//        @Override
//        protected void onPostExecute(List<VideoList> videoLists) {
//            super.onPostExecute(videoLists);
//            adapter = new VideoAdapter(videoLists);
//            listview_picassoTest.setAdapter(adapter);
//            dialog.dismiss();
//        }
//    }
}
