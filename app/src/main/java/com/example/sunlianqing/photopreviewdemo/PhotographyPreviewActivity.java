package com.example.sunlianqing.photopreviewdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.sunlianqing.photopreviewdemo.model.PhotographyOutSideModel;
import com.example.sunlianqing.photopreviewdemo.model.PhotographyPreviewModel;
import com.example.sunlianqing.photopreviewdemo.model.TestData;
import com.example.sunlianqing.photopreviewdemo.util.ImageLoader;
import com.example.sunlianqing.photopreviewdemo.util.ListUtils;
import com.example.sunlianqing.photopreviewdemo.util.ZoomOutPageTransformer;
import com.example.sunlianqing.photopreviewdemo.view.LinearDotTransform;
import com.example.sunlianqing.photopreviewdemo.view.SideViewPager;

import java.util.ArrayList;
import java.util.List;


/**
 * @author sunlianqing
 */
public class PhotographyPreviewActivity extends AppCompatActivity implements
		ViewPager.OnPageChangeListener, PhotographyPreviewAdapter.OnClickChangeLayout {
	public final static String PHOTO_LIST_BEAN = "photo_list_bean";
	public final static String PAGE = "page";
	public final static String PAGE_SIZE = "page_size";
	public final static String POSITION = "position";
	public static final String TID = "tId";
	public final static String FROM = "from";
	public final static String FROM_BACK = "from_back";
	public final static String FROM_FRONT = "from_front";

	private FrameLayout mFrameLayout;
	private ImageView mFrameLayoutImg;
	private SideViewPager mViewPager;
	private LinearDotTransform mDotTransform;
	private LinearLayout previewItemLyBottom;

	private PhotographyPreviewAdapter adapter;

	private ArrayList<PhotographyPreviewModel.DataBean> photographyPreviewModelList = new ArrayList<>();
	private int position = 0;
	private int listIndex = 0;
	private String from = FROM_FRONT;
	private int isPageSelected = 0;
	private String tid = "";
	private int page = 1;

	private boolean isLastPage = false;
	private boolean canJumpBackPage = true;
	private boolean isFirstPage = false;
	private boolean canJumpFrontPage = true;
	private boolean isDragPage = false;
	private boolean onlyOnePage = false;

	public static void jump(AppCompatActivity context, String tid) {
		context.startActivity(new Intent(context, PhotographyPreviewActivity.class).putExtra(TID, tid));
	}

	/**
	 *
	 * @param context
	 * @param list       取点击的当前position 到 page * pageSize + pageSize的数据
	 * @param position
	 */
	public static void jump(AppCompatActivity context, List<PhotographyOutSideModel.ListBean> list, int position) {
		context.startActivity(new Intent(context, PhotographyPreviewActivity.class)
				.putExtra(PAGE, (position - 1) / 10 + 1)
				.putExtra(POSITION, (position - 1) % 10)
				.putParcelableArrayListExtra(PHOTO_LIST_BEAN,
						PhotographyPreviewModel.parseListBeanToPreviewModelList(list)));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_preview_activity);
		initView();
		getExtra();
	}

	private void getExtra() {
		if (getIntent() != null && !ListUtils.isEmpty(getIntent().getParcelableArrayListExtra(PHOTO_LIST_BEAN))) {
			photographyPreviewModelList = getIntent().getParcelableArrayListExtra(PHOTO_LIST_BEAN);
			page = getIntent().getIntExtra(PAGE, 1);
			listIndex = getIntent().getIntExtra(POSITION, 0);
			if (!TextUtils.isEmpty(getIntent().getStringExtra(FROM))) {
				from = getIntent().getStringExtra(FROM);
			}
			setData(photographyPreviewModelList.get(listIndex));
		} else {
			onlyOnePage = true;
			tid = getIntent().getStringExtra(TID);
			// 获取单张
			// getData();
		}
	}

	private void initView() {
		mFrameLayout = (FrameLayout) findViewById(R.id.photo_preview_view_bg);
		mFrameLayoutImg = (ImageView) findViewById(R.id.photo_preview_view_bg_img);
		mViewPager = (SideViewPager) findViewById(R.id.photo_preview_view_pager);
		mDotTransform = (LinearDotTransform) findViewById(R.id.photo_preview_view_dot_transform);
		previewItemLyBottom = (LinearLayout) findViewById(R.id.preview_item_ly_bottom);

		mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
		mViewPager.setOffscreenPageLimit(3);
		mViewPager.addOnPageChangeListener(this);
		mViewPager.setOnSideListener(new SideViewPager.onSideListener() {
			@Override
			public void onLeftSide() {
				judgeResetPicDataBack();
			}

			@Override
			public void onRightSide() {
				judgeResetPicDataFront();
			}
		});
	}

	public void setData(final PhotographyPreviewModel.DataBean dataBean) {
		if (dataBean != null) {
			if (!ListUtils.isEmpty(dataBean.showPicList)) {
				adapter = new PhotographyPreviewAdapter(this, dataBean.showPicList);
				mViewPager.setAdapter(adapter);
				adapter.setOnClickChangeLyListener(this);
				if (from.equals(FROM_BACK)) {
					mViewPager.setCurrentItem(adapter.getCount() - 1);
				} else {
					mViewPager.setCurrentItem(0);
				}
				if (adapter.getCount() > 1) {
					mDotTransform.setVisibility(View.VISIBLE);
					mDotTransform.createTabItems(adapter.getCount(), mViewPager.getCurrentItem());
				} else {
					mDotTransform.setVisibility(View.GONE);
				}
				mFrameLayout.post(new Runnable() {
					@Override
					public void run() {
						ImageLoader.loadImageBlur(mFrameLayoutImg, adapter.getBackgroundUrl(0));
					}
				});
			}
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {
		this.position = position;
		mDotTransform.setUpSelected(position);

		if (isPageSelected == 2) {
			isPageSelected = 0;
			ImageLoader.loadImageBlur(mFrameLayoutImg, adapter.getBackgroundUrl(position));
		} else {
			isPageSelected = 1;
		}

		isLastPage = position == adapter.getCount() - 1;
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		if (state == 0) {// 等待切换动画执行完毕
			if (isPageSelected == 1) {
				isPageSelected = 0;
				ImageLoader.loadImageBlur(mFrameLayoutImg, adapter.getBackgroundUrl(position));
			} else {
				isPageSelected = 2;
			}
		}
		isDragPage = state == 1;
	}

	@Override
	public void onClick(final String url) {
		if (previewItemLyBottom.getAlpha() == 1) {
			previewItemLyBottom.animate()
					.alpha(0f)
					.setDuration(600)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							previewItemLyBottom.setVisibility(View.GONE);
						}
					});

		} else {
			previewItemLyBottom.setVisibility(View.VISIBLE);
			previewItemLyBottom.animate()
					.alpha(1f)
					.setDuration(600).setListener(null);
		}
	}

	private void judgeResetPicDataFront() {
		if (!onlyOnePage && isDragPage) {
			isLastPage = position == adapter.getCount() - 1;
			canJumpFrontPage = isLastPage ? true : false;
			if (listIndex < (photographyPreviewModelList.size() - 1) && photographyPreviewModelList != null) {
				if (photographyPreviewModelList.get(listIndex) != null && !ListUtils.isEmpty(photographyPreviewModelList.get(listIndex).showPicList)
						&& photographyPreviewModelList.get(listIndex).showPicList.size() == 1) {
					//如果只有一张图
					if (canJumpFrontPage) {
						canJumpFrontPage = false;
						listIndex++;
						if (photographyPreviewModelList.get(listIndex) != null && !ListUtils.isEmpty(photographyPreviewModelList.get(listIndex).showPicList)) {
							PhotographyPreviewActivity.this.finish();
							PhotographyPreviewActivity.this.startActivity(new Intent(PhotographyPreviewActivity.this, PhotographyPreviewActivity.class)
									.putExtra(PAGE, page)
									.putExtra(POSITION, listIndex)
									.putExtra(FROM, FROM_FRONT)
									.putParcelableArrayListExtra(PHOTO_LIST_BEAN, photographyPreviewModelList));
						}
					}
				} else {
					if (isLastPage && canJumpFrontPage) {
						canJumpFrontPage = false;
						listIndex++;
						if (photographyPreviewModelList.get(listIndex) != null && !ListUtils.isEmpty(photographyPreviewModelList.get(listIndex).showPicList)) {
							PhotographyPreviewActivity.this.finish();
							PhotographyPreviewActivity.this.startActivity(new Intent(PhotographyPreviewActivity.this, PhotographyPreviewActivity.class)
									.putExtra(PAGE, page)
									.putExtra(POSITION, listIndex)
									.putExtra(FROM, FROM_FRONT)
									.putParcelableArrayListExtra(PHOTO_LIST_BEAN, photographyPreviewModelList));
						}
					}
				}
			} else {
				page++;
				getListData(page, FROM_FRONT);
			}
		}
	}

	private void judgeResetPicDataBack() {
		if (!onlyOnePage && isDragPage) {
			isFirstPage = position == 0;
			canJumpBackPage = isFirstPage ? true : false;
			if (listIndex > 0 && photographyPreviewModelList != null) {
				if (photographyPreviewModelList.get(listIndex) != null && !ListUtils.isEmpty(photographyPreviewModelList.get(listIndex).showPicList)
						&& photographyPreviewModelList.get(listIndex).showPicList.size() == 1) {
					//如果只有一张图
					if (canJumpBackPage) {
						canJumpBackPage = false;
						listIndex--;
						if (photographyPreviewModelList.get(listIndex) != null && !ListUtils.isEmpty(photographyPreviewModelList.get(listIndex).showPicList)) {
							PhotographyPreviewActivity.this.finish();
							PhotographyPreviewActivity.this.startActivity(new Intent(PhotographyPreviewActivity.this, PhotographyPreviewActivity.class)
									.putExtra(PAGE, page)
									.putExtra(POSITION, listIndex)
									.putExtra(FROM, FROM_BACK)
									.putParcelableArrayListExtra(PHOTO_LIST_BEAN, photographyPreviewModelList));
							PhotographyPreviewActivity.this.overridePendingTransition(R.anim.in_to_left, R.anim.out_to_left);

						}
					}
				} else {
					if (isFirstPage && canJumpBackPage) {
						canJumpBackPage = false;
						listIndex--;
						if (photographyPreviewModelList.get(listIndex) != null && !ListUtils.isEmpty(photographyPreviewModelList.get(listIndex).showPicList)) {
							PhotographyPreviewActivity.this.finish();
							PhotographyPreviewActivity.this.startActivity(new Intent(PhotographyPreviewActivity.this, PhotographyPreviewActivity.class)
									.putExtra(PAGE, page)
									.putExtra(POSITION, listIndex)
									.putExtra(FROM, FROM_BACK)
									.putParcelableArrayListExtra(PHOTO_LIST_BEAN, photographyPreviewModelList));
							PhotographyPreviewActivity.this.overridePendingTransition(R.anim.in_to_left, R.anim.out_to_left);

						}
					}
				}
			} else {
				if (page > 1) {
					page--;
					getListData(page, FROM_BACK);
				}
			}
		}
	}

	private void getListData(final int tempPage, final String from) {
		List<PhotographyOutSideModel.ListBean> list = TestData.getList();
		if (!ListUtils.isEmpty(list)) {
			if (from.equals(FROM_BACK)) {
				listIndex = TestData.PAGE_SIZE - 1;
			} else {
				listIndex = 0;
			}
			if (!ListUtils.isEmpty(PhotographyPreviewModel.parseListBeanToPreviewModelList(TestData.getList()))) {
				photographyPreviewModelList = PhotographyPreviewModel.parseListBeanToPreviewModelList(TestData.getList());
			}
			if (PhotographyPreviewModel.parseListBeanToPreviewModel(list.get(listIndex)) != null
					&& !ListUtils.isEmpty(PhotographyPreviewModel.parseListBeanToPreviewModel(list.get(listIndex)).showPicList)) {
				PhotographyPreviewActivity.this.finish();
				PhotographyPreviewActivity.this.startActivity(new Intent(PhotographyPreviewActivity.this, PhotographyPreviewActivity.class)
						.putExtra(PAGE, tempPage)
						.putExtra(POSITION, listIndex)
						.putExtra(FROM, from)
						.putParcelableArrayListExtra(PHOTO_LIST_BEAN, photographyPreviewModelList));
				if (from.equals(FROM_BACK)) {
					PhotographyPreviewActivity.this.overridePendingTransition(R.anim.in_to_left, R.anim.out_to_left);
				}
			}
		} else {
			if (from.equals(FROM_BACK)) {
				page++;
			} else {
				page--;
			}
		}
	}


}
