package com.clique.utils;

import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication extends MultiDexApplication {
	@Override
	public void onCreate() {
		super.onCreate();
		Fresco.initialize(this);
	}
}