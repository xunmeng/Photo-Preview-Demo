package com.example.sunlianqing.photopreviewdemo.model;

import android.os.Parcel;
import android.os.Parcelable;


import com.example.sunlianqing.photopreviewdemo.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunlianqing
 */
public class PhotographyPreviewModel extends BaseResult {
	public DataBean data;

	public static class DataBean implements Parcelable {
		public String tId;
		public List<String> showPicList;

		DataBean() {

		}

		protected DataBean(Parcel in) {
			tId = in.readString();
			showPicList = in.createStringArrayList();
		}

		public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
			@Override
			public DataBean createFromParcel(Parcel in) {
				return new DataBean(in);
			}

			@Override
			public DataBean[] newArray(int size) {
				return new DataBean[size];
			}
		};

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeString(this.tId);
			dest.writeStringList(this.showPicList);
		}
	}

	public static PhotographyPreviewModel.DataBean parseListBeanToPreviewModel(PhotographyOutSideModel.ListBean listBean) {
		PhotographyPreviewModel.DataBean dataBean = new PhotographyPreviewModel.DataBean();
		dataBean.tId = String.valueOf(listBean.tId);
		dataBean.showPicList = listBean.showPicList;
		return dataBean;
	}

	public static ArrayList<DataBean> parseListBeanToPreviewModelList(List<PhotographyOutSideModel.ListBean> listBean) {
		ArrayList<DataBean> dataBeansList = new ArrayList<>();
		if (!ListUtils.isEmpty(listBean)) {
			for (int i = 0; i < listBean.size(); i++) {
				PhotographyPreviewModel.DataBean dataBean = parseListBeanToPreviewModel(listBean.get(i));
				dataBeansList.add(dataBean);
			}
		}
		return dataBeansList;
	}
}
