package com.just.test.tools;

import android.app.Application;

public class SSEApp extends Application{

	private static SSEApp mInstance;

    public static SSEApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        mInstance = this;
    }
	
}
