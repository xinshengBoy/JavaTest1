package com.just.test.tools;

import android.content.Context;
import android.graphics.Bitmap;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 通用viewhodler
 * Created by user on 2016/1/25.
 */
public class ViewHolder {
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    private ViewHolder(Context context, ViewGroup parent, int layoutId,
                       int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        // setTag
        mConvertView.setTag(this);
    }


    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return .
     */
    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 74.     * 为TextView设置字符串
     * 75.     *
     * 76.     * @param viewId
     * 77.     * @param text
     * 78.     * @return
     * 79.
     */
    public ViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */


    public ViewHolder setImageResource(int viewId, int drawableId)


    {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;

    }


    /**
     * 103.     * 为ImageView设置图片
     * 104.     *
     * 105.     * @param viewId
     * 106.     * @param drawableId
     * 107.     * @return
     * 108.
     */


    public ViewHolder setImageBitmap(int viewId, Bitmap bm)


    {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;

    }


    /**
     * 117.     * 为ImageView设置图片
     * 118.     *
     * 119.     * @param viewId
     * 120.     * @param drawableId
     * 121.     * @return
     * 122.
     */


    public ViewHolder setImageByUrl(int viewId, String url)


    {
//        ImageLoader.getInstance().loadImage(url,
//                (ImageView) getView(viewId));
        return this;

    }


    public int getPosition()


    {
        return mPosition;

    }


}

