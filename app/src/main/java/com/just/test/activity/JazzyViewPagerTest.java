package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.viewpager.JazzyViewPager;
import com.just.test.viewpager.OutlineContainer;
import com.just.test.widget.MyActionBar;

/**
 * viewpager的一种切换效果
 * https://github.com/jfeinstein10/JazzyViewPager
 * Created by admin on 2017/5/15.
 */

public class JazzyViewPagerTest extends Activity {

    private JazzyViewPager viewpager_jazzy;
    private int [] imgs = new int[]{R.drawable.bg01,R.drawable.bg02,R.drawable.bg03,R.drawable.bg04,R.drawable.beautifalgirl,R.drawable.friend,R.drawable.sms};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_jazzy_viewpager);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "viewpager的一种切换效果");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        viewpager_jazzy = (JazzyViewPager)findViewById(R.id.viewpager_jazzy);
        viewpager_jazzy.setTransitionEffect(JazzyViewPager.TransitionEffect.CubeIn);//可有多个效果，在此处设置
        viewpager_jazzy.setAdapter(new JazzyViewPagerAdapter());
    }

    private class JazzyViewPagerAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(JazzyViewPagerTest.this);
            imageView.setBackgroundResource(imgs[position]);
            container.addView(imageView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            viewpager_jazzy.setObjectForPosition(imageView,position);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewpager_jazzy.findViewFromObject(position));
        }

        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            if (view instanceof OutlineContainer){
                return ((OutlineContainer)view).getChildAt(0) == object;
            }else {
                return view == object;
            }
        }
    }
}
