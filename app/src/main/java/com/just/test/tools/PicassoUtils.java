package com.just.test.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by Administrator on 2017/3/2.
 */

public class PicassoUtils {

    /**
     *调整图片大小
     */
    public static void loadImageSize(Context context, String path, int width, int height, ImageView imageView){
        Picasso.with(context).load(path).resize(width,height).centerCrop().into(imageView);
    }

    /**
     * 设置默认图片
     */
    public static void loadImageViewHodler(Context context,String path,int resId,ImageView imageView){
        Picasso.with(context).load(path).fit().placeholder(resId).into(imageView);
    }

    /**
     * 实现裁剪大小的功能
     */
    public static void loadImageWithCrop(Context context,String path,ImageView imageView){
        Picasso.with(context).load(path).transform(new CropSqureTranslation()).into(imageView);
    }

    /**
     * 实现对图片的自定义裁剪
     */
    public static class CropSqureTranslation implements Transformation{

        @Override
        public Bitmap transform(Bitmap bitmap) {
            int size = Math.min(bitmap.getWidth(),bitmap.getHeight());
            int x = (bitmap.getWidth() - size)/2;
            int y = (bitmap.getHeight() - size )/2;

            Bitmap result = Bitmap.createBitmap(bitmap,x,y,size,size);
            if (result != null){
                bitmap.recycle();//释放内存
            }
            return result;
        }

        @Override
        public String key() {
            //命名
            return "squre";
        }
    }
}
