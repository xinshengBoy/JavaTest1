package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.just.test.R;
import com.just.test.widget.ImageLoader;
import com.just.test.widget.MyActionBar;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 广告栏  快速集成广告栏效果，可无限循环
 * https://github.com/saiwu-bigkoo/Android-ConvenientBanner
 * compile 'com.bigkoo:convenientbanner:2.0.5'
 * 可参考http://blog.csdn.net/duolaimila/article/details/51025729
 * Created by admin on 2017/5/15.
 *
 * viewpager切换动画库
 *https://github.com/ToxicBakery/ViewPagerTransforms
 * compile 'com.ToxicBakery.viewpager.transforms:view-pager-transforms:1.2.32@aar'
 * 有以下类别
 * DefaultTransformer,AccordionTransformer,BackgroundToForegroundTransformer,CubeInTransformer
 * CubeOutTransformer,DepthPageTransformer,FlipHorizontalTransformer,FlipVerticalTransformer
 * ForegroundToBackgroundTransformer,RotateDownTransformer,RotateUpTransformer,ScaleInOutTransformer
 * StackTransformer,TabletTransformer,ZoomInTransformer,ZoomOutSlideTransformer,ZoomOutTransformer
 * DrawerTransformer等种类
 *
 *
 * Glide是一款基于Android的图片加载和图片缓存组件，它可以最大性能地在Android设备上读取、解码、显示图片和视频。
 * Glide可以将远程的图片、视频、动画图片等缓存在设备本地，便于提高用户浏览图片的流畅体验。
 * glide图片加载库，会自动做图片的缓冲，比imageloader好用多了
 * http://blog.csdn.net/shangmingchao/article/details/51125554/
 *  compile 'com.github.bumptech.glide:glide:3.7.0'
 *
 *  可参考http://www.codeceo.com/article/android-glide.html
 */

public class ConvenientBannerTest extends Activity {

    private ConvenientBanner banner_convennient_local,banner_convennient_network;//
    private List<Integer> localImages = new ArrayList<>();
    private String[] images = {
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://img2.3lian.com/2014/f2/37/d/41.jpg",
            "http://img2.3lian.com/2014/f2/37/d/42.jpg",
            "http://img2.3lian.com/2014/f2/37/d/43.jpg",
            "http://img2.3lian.com/2014/f2/37/d/44.jpg"
    };
    private List<String> networkImages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_convenient_banner);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "广告栏");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initData();
//        initNetWorkImageData();

        banner_convennient_local = (ConvenientBanner) findViewById(R.id.banner_convennient_local);
        banner_convennient_network = (ConvenientBanner) findViewById(R.id.banner_convennient_network);
        //本地图片加载
        banner_convennient_local.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, localImages)
                .setPageIndicator(new int[]{R.drawable.point_check, R.drawable.point_nocheck})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(ConvenientBannerTest.this, "即将打开第" + localImages.get(position) + "链接", Toast.LENGTH_SHORT).show();
                    }
                });
        banner_convennient_local.setManualPageable(false);//设置不能手动影响
        //网络图片加载
        banner_convennient_network.startTurning(4000);
        banner_convennient_network.setPageTransformer(new AccordionTransformer());//设置翻页效果
        networkImages = Arrays.asList(images);
        banner_convennient_network.setPages(new CBViewHolderCreator<NetWorkImageHolderView>() {
            @Override
            public NetWorkImageHolderView createHolder() {
                return new NetWorkImageHolderView();
            }
        },networkImages)
                .setPageIndicator(new int[]{R.drawable.point_check, R.drawable.point_nocheck})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(ConvenientBannerTest.this, "即将打开第" + networkImages.get(position) + "链接", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**本地图片加载**/
    public class LocalImageHolderView implements Holder<Integer> {

        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }
    }
    /**网络图片加载**/
    public class NetWorkImageHolderView implements Holder<String> {

        private ImageView image;
        @Override
        public View createView(Context context) {
            image = new ImageView(context);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            return image;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            image.setImageResource(R.drawable.empty_photo);
            Glide.with(context).load(data).into(image);
//            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(data,image);
        }
    }
    private void initData() {
        localImages.add(R.drawable.bg01);
        localImages.add(R.drawable.bg02);
        localImages.add(R.drawable.bg03);
        localImages.add(R.drawable.bg04);
    }

    private void initNetWorkImageData(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.empty_photo)
                .cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ConvenientBannerTest.this)
                .defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY -2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开启自动翻页
        banner_convennient_local.startTurning(3000);
        banner_convennient_network.startTurning(3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //停止自动翻页
        banner_convennient_local.stopTurning();
        banner_convennient_network.stopTurning();
    }
}
