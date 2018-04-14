package com.example.sunlianqing.photopreviewdemo.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * @author sunlianqing
 */
public class SideViewPager extends ViewPager {

	/**
	 * 开始点击的位置
	 */
	private int startX;
	/**
	 * 临界值
	 */
	private int criticalValue = 100;

	private long lastRightTime = 0;

	private long lastLeftTime = 0;

	/**
	 * 边界滑动回调
	 */
	public interface onSideListener {
		/**
		 * 左边界回调
		 */
		void onLeftSide();

		/**
		 * 右边界回调
		 */
		void onRightSide();
	}

	/**
	 * 回调
	 */
	private onSideListener mOnSideListener;

	/**
	 * 设置回调
	 *
	 * @param listener
	 */
	public void setOnSideListener(onSideListener listener) {
		this.mOnSideListener = listener;
	}

	/**
	 * 设置临界值
	 *
	 * @param criticalValue
	 */
	public void setCriticalValue(int criticalValue) {
		this.criticalValue = criticalValue;
	}

	public SideViewPager(Context context) {
		this(context, null);
	}

	public SideViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = (int) event.getX();
				break;
		}
		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE:
				if (startX - event.getX() > criticalValue && (getCurrentItem() == getAdapter().getCount() - 1)) {
					if (null != mOnSideListener) {
						if ((System.currentTimeMillis() - lastRightTime) > 1000) {
							lastRightTime = System.currentTimeMillis();
							mOnSideListener.onRightSide();
						}
					}
				}
				if ((event.getX() - startX) > criticalValue && (getCurrentItem() == 0)) {
					if (null != mOnSideListener) {
						if ((System.currentTimeMillis() - lastLeftTime) > 1000) {
							lastLeftTime = System.currentTimeMillis();
							mOnSideListener.onLeftSide();
						}
					}
				}
				break;
			default:
				break;
		}
		return super.onTouchEvent(event);
	}
}