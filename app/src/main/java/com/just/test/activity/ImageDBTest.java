package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.bean.FilmImage;
import com.just.test.sqlite.ImageDBHelper;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片数据库
 * Created by admin on 2017/5/26.
 */

public class ImageDBTest extends Activity implements View.OnClickListener{

    private ListView listview_filmimage;
    private TextView txt_filmimage_notice;
    private ProgressBar pb_filmimage;
    private Button btn_filmimage_create;
    private ImageDBHelper db;
    private List<FilmImage> mList = new ArrayList<>();
    private int positions;
    private FilmImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_filmimage);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "电影海报列表");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        db = new ImageDBHelper(ImageDBTest.this);
        initView();
    }

    private void initView(){
        listview_filmimage = (ListView)findViewById(R.id.listview_filmimage);
        txt_filmimage_notice = (TextView)findViewById(R.id.txt_filmimage_notice);
        pb_filmimage = (ProgressBar)findViewById(R.id.pb_filmimage);
        btn_filmimage_create = (Button)findViewById(R.id.btn_filmimage_create);

        listview_filmimage.setVisibility(View.GONE);
        txt_filmimage_notice.setVisibility(View.GONE);
        pb_filmimage.setVisibility(View.VISIBLE);

        btn_filmimage_create.setOnClickListener(this);
        mHandler.sendEmptyMessage(0);//开始查询数据库
    }

    @Override
    public void onClick(View view) {
        if (view == btn_filmimage_create){
            Intent intent = new Intent(ImageDBTest.this,FilmImageDetail.class);
            intent.putExtra("type","0");//0代表新建，1代表修改
            startActivityForResult(intent,0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0){
            mList.clear();
            mHandler.sendEmptyMessage(0);
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                checkFilmImageData();
            }else if (msg.what == 1){
                pb_filmimage.setVisibility(View.GONE);
                if (mList.size() == 0){
                    txt_filmimage_notice.setVisibility(View.VISIBLE);
                    listview_filmimage.setVisibility(View.GONE);
                }else {
                    txt_filmimage_notice.setVisibility(View.GONE);
                    listview_filmimage.setVisibility(View.VISIBLE);
                    adapter = new FilmImageAdapter(ImageDBTest.this,mList,R.layout.item_filmimage);
                    listview_filmimage.setAdapter(adapter);
                    //修改
                    listview_filmimage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(ImageDBTest.this,FilmImageDetail.class);
                            intent.putExtra("type","1");
                            intent.putExtra("filmImage_id",mList.get(i).getId());
                            //不能使用intent传对象，因为对象里面有图片，会超过最大传输值得限制，所以传个id过去，到了那边再去查询
                            startActivityForResult(intent,0);
                        }
                    });
                    //删除
                    listview_filmimage.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                            positions = i;
                            showUpdateDialog(ImageDBTest.this,"提示","你确定要删除这条数据么",mList.get(i).getId());
                            return true;
                        }
                    });
                }
            }else if (msg.what == 2){
                LemonBubble.showRight(ImageDBTest.this,"删除成功",2000);
//                mList.remove(positions);
//                adapter.update(mList);
//                adapter.notifyDataSetChanged();
                mList.clear();
                checkFilmImageData();
            }
        }
    };

    /**
     * 查询数据库所有数据
     */
    private void checkFilmImageData(){
        mList = db.checkAllInfo();
        mHandler.sendEmptyMessage(1);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        if (mList != null){
            mList = null;
        }
    }

    /**
     * 对话框
     * @param context 上下文
     * @param updateContent 内容
     */
    private void showUpdateDialog(final Context context, String title, String updateContent, final String id) {
        LemonHelloInfo info = new LemonHelloInfo()
                .setTitle(title)
                .setTitleColor(Color.RED)
                .setContent(updateContent)
                .setContentFontSize(15)
                .addAction(new LemonHelloAction("确定", Color.BLUE, new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        lemonHelloView.hide();
                        if (id == null){//删除数据
                            db.deleteAll();
                        }else {
                            db.delete(id);
                        }
                       mHandler.sendEmptyMessage(2);
                    }
                }))
                .addAction(new LemonHelloAction("取消", Color.GRAY, new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        lemonHelloView.hide();
                    }
                }));
        info.show(context);
    }

    private class FilmImageAdapter extends CommonAdapter<FilmImage>{

        public FilmImageAdapter(Context context, List<FilmImage> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        public void update(List<FilmImage> data){
            mDatas = data;
            notifyDataSetChanged();
        }
        @Override
        public void convert(ViewHolder helper, FilmImage item) {
            ImageView image = helper.getView(R.id.iv_item_filmimage);
            TextView name = helper.getView(R.id.txt_item_filmimage_name);
            TextView date = helper.getView(R.id.txt_item_filmimage_date);

            name.setText(item.getFilmImage_name());
            date.setText(item.getFilmImage_date());
            byte [] images = item.getFilmImage_picture();
            Bitmap bitmap = BitmapFactory.decodeByteArray(images,0,images.length);
            image.setImageBitmap(bitmap);
        }
    }
}
