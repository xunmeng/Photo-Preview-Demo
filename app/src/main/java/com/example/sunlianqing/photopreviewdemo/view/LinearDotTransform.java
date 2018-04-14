package com.example.sunlianqing.photopreviewdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.sunlianqing.photopreviewdemo.R;


/**
 * @author sunlianqing
 */
public class LinearDotTransform extends LinearLayout {

    Context mContext;

    ImageView currentImageView;

    /**
     * 焦点
     */
    int resSelect;
    /**
     * 非焦点
     */
    int resNormal;

    /**
     * 间隔距离
     */
    int interval = 10;

    public LinearDotTransform(Context context) {
        super(context);
        init(context);
    }

    public LinearDotTransform(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LinearDotTransform(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        this.mContext = context;
        resSelect = R.mipmap.ic_page_indicator;
        resNormal = R.mipmap.ic_page_indicator_focused;
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
    }


    /**
     * 设置切换图标
     * @param resSelect
     * @param resNormal
     */
    public void setResDrawable(int resSelect, int resNormal){
        this.resSelect = resSelect;
        this.resNormal = resNormal;
    }

    public void createTabItems(int count, int currentCount) {
        removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(mContext);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = interval;
            params.rightMargin = interval;
            params.topMargin = 48;
            params.bottomMargin = 0;
             imageView.setLayoutParams(params);
            if (currentCount == i) {
                imageView.setImageResource(resSelect);
                currentImageView = imageView;
            } else {
                imageView.setImageResource(resNormal);
            }
            addView(imageView);
        }
    }


    public void setUpSelected(int position) {
        if (currentImageView != null) {
            currentImageView.setImageResource(resNormal);
        }
        ImageView mImageView = (ImageView) getChildAt(position);
        if (mImageView != null) {
            mImageView.setImageResource(resSelect);
            currentImageView = mImageView;
        }
    }




}