package com.example.sunlianqing.photopreviewdemo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.request.RequestOptions;
import com.example.sunlianqing.photopreviewdemo.util.ImageLoader;
import com.example.sunlianqing.photopreviewdemo.util.ListUtils;
import com.example.sunlianqing.photopreviewdemo.view.TouchImageView;

import java.util.List;


/**
 * @author sunlianqing
 */
public class PhotographyPreviewAdapter extends PagerAdapter {
	Context mContext;
	List<String> datas;
	private OnClickChangeLayout onClickChangeLayout;


	public PhotographyPreviewAdapter(Context cxt, List<String> datas) {
		this.mContext = cxt;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	public void setData(List<String> picList) {
		if(!ListUtils.isEmpty(picList)){
			datas.clear();
			datas.addAll(picList);
			notifyDataSetChanged();
		}
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.photo_preview_item_ly, null);
		TouchImageView bgImage = (TouchImageView) view.findViewById(R.id.photo_image_view);
		if(!ListUtils.isEmpty(datas) && datas.get(position) != null){
			final String imgBg = datas.get(position);
			ImageLoader.showImage(bgImage, imgBg, new RequestOptions().placeholder(R.mipmap.sub_forum_icon).
					error(R.mipmap.sub_forum_icon).fallback(R.mipmap.sub_forum_icon));
			bgImage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onClickChangeLayout.onClick(imgBg);
				}
			});
		}
		container.addView(view, 0);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		View view = (View) object;
		container.removeView(view);
	}


	public String getBackgroundUrl(int index) {
		if (datas.size() > index) {
			String dataBean = datas.get(index);
			return dataBean;
		}
		return "";
	}

	public void setOnClickChangeLyListener(OnClickChangeLayout onClickChangeLayout) {
		this.onClickChangeLayout = onClickChangeLayout;
	}

	public interface OnClickChangeLayout {
		void onClick(String url);
	}
}