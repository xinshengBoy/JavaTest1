package com.just.test.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;

/**
 * 展开收缩的按钮
 * Created by admin on 2017/6/15.
 */

public class RadialButtonLayout extends FrameLayout implements View.OnClickListener{

    private final static long DURATION_SHORT = 400;
    private final TextView radialButton_home,radialButton_find,radialButton_chat,radialButton_friend,radialButton_setting,radialButton_menu;
    private Toast toast;
    private boolean isOpen = false;

    public RadialButtonLayout(final Context context) {
        this(context, null);
    }

    public RadialButtonLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadialButtonLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate( R.layout.layout_radial_button, this);
        radialButton_home = (TextView)view.findViewById(R.id.radialButton_home);
        radialButton_find = (TextView)view.findViewById(R.id.radialButton_find);
        radialButton_chat = (TextView)view.findViewById(R.id.radialButton_chat);
        radialButton_friend = (TextView)view.findViewById(R.id.radialButton_friend);
        radialButton_setting = (TextView)view.findViewById(R.id.radialButton_setting);
        radialButton_menu = (TextView)view.findViewById(R.id.radialButton_menu);

        radialButton_home.setOnClickListener(this);
        radialButton_find.setOnClickListener(this);
        radialButton_chat.setOnClickListener(this);
        radialButton_friend.setOnClickListener(this);
        radialButton_setting.setOnClickListener(this);
        radialButton_menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int resId;
        if (view == radialButton_home){
            resId = R.string.radialHome;
            showToast(resId);
            radialButton_home.playSoundEffect(SoundEffectConstants.CLICK);
        }else if (view == radialButton_find){
            resId = R.string.radialFind;
            showToast(resId);
            radialButton_find.playSoundEffect(SoundEffectConstants.CLICK);
        }else if (view == radialButton_chat){
            resId = R.string.radialChat;
            showToast(resId);
            radialButton_chat.playSoundEffect(SoundEffectConstants.CLICK);
        }else if (view == radialButton_friend){
            resId = R.string.radialFriend;
            showToast(resId);
            radialButton_friend.playSoundEffect(SoundEffectConstants.CLICK);
        }else if (view == radialButton_setting){
            resId = R.string.radialSetting;
            showToast(resId);
            radialButton_setting.playSoundEffect(SoundEffectConstants.CLICK);
        }else if (view == radialButton_menu){
            if (isOpen){
                //close
                hide(radialButton_home);
                hide(radialButton_find);
                hide(radialButton_chat);
                hide(radialButton_friend);
                hide(radialButton_setting);

                isOpen = false;
                resId = R.string.radialClose;
            }else {
                //open
                show(radialButton_home,1,300);
                show(radialButton_find,2,300);
                show(radialButton_chat,3,300);
                show(radialButton_friend,4,300);
                show(radialButton_setting,5,300);

                isOpen = true;
                resId = R.string.radialOpen;
            }
            showToast(resId);
            radialButton_menu.playSoundEffect(SoundEffectConstants.CLICK);
        }
    }

    private void showToast( final int resId ) {
        if ( toast != null ) {
            toast.cancel();
        }
        toast = Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT );
        toast.show();
    }

    private void hide( final View child) {
        child.animate()
                .setDuration(DURATION_SHORT)
                .translationX(0)
                .translationY(0)
                .start();
    }

    private void show(final View child, final int position, final int radius) {
        float angleDeg = 180.f;
        int dist = radius;
        switch (position) {
            case 1:
                angleDeg += 0.f;
                break;
            case 2:
                angleDeg += 45.f;
                break;
            case 3:
                angleDeg += 90.f;
                break;
            case 4:
                angleDeg += 135.f;
                break;
            case 5:
                angleDeg += 180.f;
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                break;
        }

        final float angleRad = (float) (angleDeg * Math.PI / 180.f);

        final Float x = dist * (float) Math.cos(angleRad);
        final Float y = dist * (float) Math.sin(angleRad);
        child.animate()
                .setDuration(DURATION_SHORT)
                .translationX(x)
                .translationY(y)
                .start();
    }
}
