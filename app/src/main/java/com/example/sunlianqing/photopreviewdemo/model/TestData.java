package com.example.sunlianqing.photopreviewdemo.model;

import java.util.ArrayList;
import java.util.List;



/**
 * @author sunlianqing
 */
public class TestData {

	public static int PAGE_SIZE = 3;

	public static List<PhotographyOutSideModel.ListBean> getList() {
		List<PhotographyOutSideModel.ListBean> testList = new ArrayList<>();
		for (int i = 0; i < PAGE_SIZE; i++) {
			List<String> picList = new ArrayList<>();
			picList.add("http://img.67.com/upload/images/2017/02/20/1487558462_1922560609.jpg");
			picList.add("http://img.chinatimes.com/newsphoto/2016-04-18/656/20160418004347.jpg");
			picList.add("https://storage.googleapis.com/static.smalljoys.me/2017/12/15b84c72-15ac-4493-9273-f989e895995e.jpeg");
			picList.add("https://storage.googleapis.com/static.smalljoys.me/2017/12/10d99076-f81a-49b6-9224-a5c64b61da77.jpeg");

			PhotographyOutSideModel.ListBean listBean = new PhotographyOutSideModel.ListBean();
			listBean.tId = i;
			listBean.showPicList = picList;
			testList.add(listBean);
		}
		return testList;
	}
}
