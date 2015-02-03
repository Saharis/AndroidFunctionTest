package com.virgil.aft.business.view.bean;

import java.util.ArrayList;

/**
 * @author Generator
 * 退款信息List
 * 31001901
 */
public class RefundInformationModel {
	
	/**Bill单号*/
	public String billNo = "";
	
	/**Bill单金额*/
	public int amount = 0;
	
	/**退款进度信息*/
	public ArrayList<RefundProcessInformationModel> refundProcessInfoList = new ArrayList<RefundProcessInformationModel>();
	
	
	public RefundInformationModel() {
		super();
	}

}
