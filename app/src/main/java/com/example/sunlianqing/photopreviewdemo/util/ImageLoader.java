package com.example.sunlianqing.photopreviewdemo.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.sunlianqing.photopreviewdemo.R;


/**
 * @author sunlianqing
 */
public class ImageLoader {

	private static final int CROSS_FADE_TIME = 500;
	private static GlideBlurformation glideBlurformation;


	public static void showImage(ImageView imageView, String url, RequestOptions options) {
		if (options == null) throw new NullPointerException("options is null");
		Glide.with(imageView).load(url).apply(options).into(imageView);
	}

	public static void loadImageBlur(ImageView imageView, String url) {
		if (glideBlurformation == null) {
			glideBlurformation = new GlideBlurformation(imageView.getContext());
		}
		Glide.with(imageView).load(url).apply(RequestOptions.bitmapTransform(glideBlurformation).placeholder(
				R.drawable.background_drawable)).
				transition(new DrawableTransitionOptions().crossFade(CROSS_FADE_TIME)).into(imageView);
	}
}
