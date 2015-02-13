package com.virgil.aft.business.view.bean;

import java.util.ArrayList;

/**
 * @author Generator
 * 退款信息List
 * 31001901
 */
public class RefundInformationModel  implements Cloneable{
	
	/**Bill单号*/
	public String billNo = "";
	
	/**货币符号*/
	public String currency = "";
	
	/**Bill单金额*/
	public PriceType amount = new PriceType();
	
	/**退款进度信息*/
	public ArrayList<RefundProcessInformationModel> refundProcessInfoList = new ArrayList<RefundProcessInformationModel>();
	
	
	public RefundInformationModel() {
		super();
	}


}
