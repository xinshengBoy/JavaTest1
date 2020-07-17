package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * imageswitcher
 * Created by user on 2016/12/28.
 */

public class ImageSwitcherTest extends Activity implements ViewSwitcher.ViewFactory{

    private ImageSwitcher is;
    private Gallery gallery_is;
    private Integer[] mThumbIds = { R.drawable.bg01, R.drawable.bg02,
            R.drawable.bg03, R.drawable.bg04, R.drawable.beautifalgirl };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_imageswitcher);
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "imageswitcher");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        is = (ImageSwitcher)findViewById(R.id.is_imageswitcher_test);
        gallery_is = (Gallery)findViewById(R.id.gallery_imageswitcher_test);

        is.setFactory(this);
        is.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        is.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));

        gallery_is.setAdapter(new ImageAdapter(this));
        gallery_is.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                is.setImageResource(mThumbIds[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public class ImageAdapter extends BaseAdapter {

        private Context mContext;

        public ImageAdapter(Context context) {
            mContext = context;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView image = new ImageView(mContext);

            image.setImageResource(mThumbIds[position]);
            image.setAdjustViewBounds(true);
            image.setLayoutParams(new Gallery.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            return image;
        }
    }

    @Override
    public View makeView() {
        ImageView image = new ImageView(this);
        image.setMinimumHeight(200);
        image.setMinimumWidth(200);
        image.setScaleType(ImageView.ScaleType.FIT_START);
        image.setLayoutParams(new ImageSwitcher.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        return image;
    }
}
