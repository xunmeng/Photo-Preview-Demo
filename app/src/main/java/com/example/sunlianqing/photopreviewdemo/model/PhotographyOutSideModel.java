package com.example.sunlianqing.photopreviewdemo.model;


import java.util.List;

/**
 * @author sunlianqing
 */
public class PhotographyOutSideModel extends BaseResult {
	public ListBean data;

	public static class ListBean extends BaseResult {
		public long tId;
		public List<String> showPicList;
	}

}
