package com.just.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.just.test.R;
import jp.wasabeef.blurry.Blurry;

/**
 * Blurry 图片模糊库
 * https://github.com/wasabeef/Blurry
 * compile 'jp.wasabeef:blurry:2.1.1'
 * Created by admin on 2017/4/24.
 */

public class BlurryActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_blurry);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                long startMs = System.currentTimeMillis();
                Blurry.with(BlurryActivity.this)
                        .radius(25)
                        .sampling(1)
                        .color(Color.argb(66, 0, 255, 255))
                        .async()
                        .capture(findViewById(R.id.right_top))
                        .into((ImageView) findViewById(R.id.right_top));

                Blurry.with(BlurryActivity.this)
                        .radius(10)
                        .sampling(8)
                        .async()
                        .capture(findViewById(R.id.right_bottom))
                        .into((ImageView) findViewById(R.id.right_bottom));

                Blurry.with(BlurryActivity.this)
                        .radius(25)
                        .sampling(1)
                        .color(Color.argb(66, 255, 255, 0))
                        .async()
                        .capture(findViewById(R.id.left_bottom))
                        .into((ImageView) findViewById(R.id.left_bottom));

                Log.d(getString(R.string.app_name),
                        "TIME " + String.valueOf(System.currentTimeMillis() - startMs) + "ms");
            }
        });

        findViewById(R.id.button).setOnLongClickListener(new View.OnLongClickListener() {

            private boolean blurred = false;

            @Override public boolean onLongClick(View v) {
                if (blurred) {
                    Blurry.delete((ViewGroup) findViewById(R.id.content));
                } else {
                    long startMs = System.currentTimeMillis();
                    Blurry.with(BlurryActivity.this)
                            .radius(25)
                            .sampling(2)
                            .async()
                            .animate(500)
                            .onto((ViewGroup) findViewById(R.id.content));
                    Log.d(getString(R.string.app_name),
                            "TIME " + String.valueOf(System.currentTimeMillis() - startMs) + "ms");
                }

                blurred = !blurred;
                return true;
            }
        });
    }

}