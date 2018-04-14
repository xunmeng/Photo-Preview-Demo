package com.example.sunlianqing.photopreviewdemo.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @author sunlianqing
 */
public class BaseResult implements Parcelable {

	private int errno;
	private String errmsg;

	public BaseResult() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.errno);
		dest.writeString(this.errmsg);
	}

	protected BaseResult(Parcel in) {
		this.errno = in.readInt();
		this.errmsg = in.readString();
	}

	public static final Creator<BaseResult> CREATOR = new Creator<BaseResult>() {
		@Override
		public BaseResult createFromParcel(Parcel source) {
			return new BaseResult(source);
		}

		@Override
		public BaseResult[] newArray(int size) {
			return new BaseResult[size];
		}
	};
}
