package com.just.test.custom;

import android.os.Handler;

/**
 * 广告评论滚动显示
 * Created by admin on 2017/6/29.
 */

public class AdvancedSwitcher {
    private AdvTextSwitcher advTsView;
    private boolean isPaused;
    private int mDuration=1000;

    public AdvancedSwitcher()
    {}

    public AdvancedSwitcher(AdvTextSwitcher view, int duration)
    {
        this.advTsView = view;
        this.mDuration = duration;
    }

    public AdvancedSwitcher setDuration(int duration)
    {
        this.mDuration = duration;
        return this;
    }

    public AdvancedSwitcher attach(AdvTextSwitcher view)
    {
        this.pause();
        this.advTsView = view;
        return this;
    }

    public void start()
    {
        isPaused = false;
        if (this.advTsView != null)
        {
            hlUpdt.postDelayed(rbUpdt, mDuration);
        }
    }

    public void pause()
    {
        isPaused = true;
    }

    public Handler hlUpdt = new Handler();

    public Runnable rbUpdt = new Runnable(){
        @Override
        public void run()
        {
            if (!isPaused && advTsView != null)
            {
                advTsView.next();
                hlUpdt.postDelayed(this, mDuration);
            }
        }
    };
}
