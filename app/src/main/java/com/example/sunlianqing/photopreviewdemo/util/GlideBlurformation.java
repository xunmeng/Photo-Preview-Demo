package com.example.sunlianqing.photopreviewdemo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;


/**
 * @author sunlianqing
 */

public class GlideBlurformation extends BitmapTransformation {
	private Context context;
	// goss 模糊程度
	private int blurRadius = 5;
	// 缩小比例
	private int scaleRatio = 5;


	public GlideBlurformation(Context context) {
		this.context = context;
	}

	@Override
	protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
		return BlurBitmapUtil.instance().blurBitmap(context, toTransform, blurRadius, scaleRatio, outWidth, outHeight);
	}

	@Override
	public void updateDiskCacheKey(MessageDigest messageDigest) {
	}

	public void setBlurRadius(int blurRadius){
		this.blurRadius = blurRadius;
	}

	public void setScaleRatio(int scaleRatio){
		this.scaleRatio = scaleRatio;
	}
}
