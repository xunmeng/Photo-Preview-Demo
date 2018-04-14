package com.example.sunlianqing.photopreviewdemo.util;


import java.util.List;

/**
 * @author sunlianqing
 */

public class ListUtils {
	public static <V> boolean isEmpty(List<V> sourceList) {
		return (sourceList == null || sourceList.size() == 0);
	}
}
