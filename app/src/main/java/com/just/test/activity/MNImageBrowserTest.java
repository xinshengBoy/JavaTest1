package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.just.test.R;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;
import com.maning.imagebrowserlibrary.MNImageBrowser;

import java.util.ArrayList;
import java.util.List;

/**
 *交互特效的图片浏览框架,微信向下滑动动态关闭,方便以后使用.
 * compile 'com.github.maning0303:MNImageBrowser:V1.0.1'
 * https://github.com/maning0303/MNImageBrowser
 * Created by admin on 2017/8/30.
 */

public class MNImageBrowserTest extends Activity {

    private GridView gridview_mnimage;
    private ArrayList<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mnimage_browswer);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "交互特效的图片");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        gridview_mnimage = (GridView)findViewById(R.id.gridview_mnimage);

        initData();

        MNImageBrowserAdapter adapter = new MNImageBrowserAdapter(MNImageBrowserTest.this,mList,R.layout.item_mnimage_browser);
        gridview_mnimage.setAdapter(adapter);

//        NineGridAdapter adapter  = new NineGridAdapter(MNImageBrowserTest.this);
//        gridview_mnimage.setAdapter(adapter);
    }

    private void initData(){
        mList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-12-18380140_455327614813449_854681840315793408_n.jpg");
        mList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-11-18380166_305443499890139_8426655762360565760_n.jpg");
        mList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-10-18382517_1955528334668679_3605707761767153664_n.jpg");
        mList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-09-18443931_429618670743803_5734501112254300160_n.jpg");
        mList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-08-18252341_289400908178710_9137908350942445568_n.jpg");
        mList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-05-18251898_1013302395468665_8734429858911748096_n.jpg");
        mList.add("http://ww1.sinaimg.cn/large/61e74233ly1feuogwvg27j20p00zkqe7.jpg");
        mList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-02-926821_1453024764952889_775781470_n.jpg");
        mList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-28-18094719_120129648541065_8356500748640452608_n.jpg");
        mList.add("https://ws1.sinaimg.cn/mw690/610dc034ly1ffwb7npldpj20u00u076z.jpg");
        mList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffv3gxs37oj20u011i0vk.jpg");
        mList.add("https://ws1.sinaimg.cn/large/610dc034ly1fftusiwb8hj20u00zan1j.jpg");
        mList.add("http://ww1.sinaimg.cn/large/610dc034ly1ffmwnrkv1hj20ku0q1wfu.jpg");
        mList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffyp4g2vwxj20u00tu77b.jpg");
        mList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffxjlvinj5j20u011igri.jpg");
        mList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-11-17881546_248332202297978_2420944671002853376_n.jpg");
        mList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-12-17662441_1675934806042139_7236493360834281472_n.jpg");
        mList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-13-17882785_926451654163513_7725522121023029248_n.jpg");
        mList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-14-17881962_1329090457138411_8289893708619317248_n.jpg");
        mList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-16-17934400_1738549946443321_2924146161843437568_n.jpg");
    }

    private class MNImageBrowserAdapter extends CommonAdapter<String> {

        private final Context context;
        private MNImageBrowserAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
            this.context = context;
        }

        @Override
        public void convert(final ViewHolder helper, String item) {
            final ImageView mnImage = helper.getView(R.id.iv_item_mnimageBrowser);
            //两张加载图片的方法都可以用，推荐用glide
            Glide.with(context)
                    .load(item)
                    .override(500,600)
                    .centerCrop()
                    .error(R.drawable.empty_photo)
                    .into(mnImage);
//            Picasso.with(context).load(item).into(mnImage);//picasso加载图片

            mnImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MNImageBrowser.showImageBrowser(context,mnImage,helper.getPosition(),mList);
                }
            });
        }
    }
}
