package com.virgil.aft.business.view.bean;

/**
 * @author Generator
 * 退款进度信息List
 * 31001901
 */
public class RefundProcessInformationModel  implements Cloneable{
	
	/**退款过程名称*/
	public String processName = "";
	
	/**相关操作时间*/
	public String processOpTime = "";
	
	/**退款过程描述*/
	public String processDesc = "";
	
	/**退款过程状态:1=已成功完成;2=已失败完成;4=进行中;8=未进行;;*/
	public int processStatus = 0;
	
	
	public RefundProcessInformationModel() {
		super();
	}

}
