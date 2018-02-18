package com.example.testpptplay;

import com.strongsoft.ui.R;

public class PlayUtils {
	/**
	 * 从R.drawable类获取str字段的值
	 * 
	 * @param str
	 * @param s
	 * @return
	 */
	public static int GetRFromDrawable(String str, Object s) {
		try {
			return R.drawable.class.getDeclaredField(str).getInt(s);
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 从R.String类获取str字段的值
	 * 
	 * @param str
	 * @param s
	 * @return
	 */
	public static int GetRFromString(String str, Object s) {
		try {
			return R.string.class.getDeclaredField(str).getInt(s);
		} catch (Exception e) {
			return -1;
		}
	}
}
