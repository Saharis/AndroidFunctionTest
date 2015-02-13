package com.virgil.aft.component;


public interface CtripExcuteDialogFragmentCallBack {
	/**
	 * 确认点击
	 * 
	 * @param tag
	 */
	public void onPositiveBtnClick(String tag);

	/**
	 * 取消点击
	 * 
	 * @param tag
	 */
	public void onNegtiveBtnClick(String tag);
}
