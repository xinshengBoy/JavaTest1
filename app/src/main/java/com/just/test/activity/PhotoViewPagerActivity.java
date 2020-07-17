package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.just.test.R;

import lecho.lib.hellocharts.view.hack.HackyViewPager;
import uk.co.senab.photoview.PhotoView;

/**
 * PhotoView
 https://github.com/chrisbanes/PhotoView
 * photoview实现图片翻页
 * Created by admin on 2017/4/5.
 */

public class PhotoViewPagerActivity extends Activity {

    private HackyViewPager hackyViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_photoview_pager);

        hackyViewPager = (HackyViewPager)findViewById(R.id.hackyViewPager);
        hackyViewPager.setAdapter(new PhotoViewAdapter());
    }

    private class PhotoViewAdapter extends PagerAdapter{

        private int[] drawables = {R.drawable.bg01,R.drawable.bg02,R.drawable.bg03,R.drawable.bg04,R.drawable.a1,R.drawable.a2};
        @Override
        public int getCount() {
            return drawables.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(PhotoViewPagerActivity.this);
            photoView.setImageResource(drawables[position]);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }
    }
}
