package com.virgil.aft.component;


public interface CtripSpaceAndCancelCallBack {
	/**
	 * 空白点击回调
	 * 
	 * @param tag
	 */
	public void onSpaceClick(String tag);

	/**
	 * 取消事件回调
	 * 
	 * @param tag
	 */
	public void onCanceled(String tag);
}
