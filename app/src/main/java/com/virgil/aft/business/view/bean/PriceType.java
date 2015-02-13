package com.virgil.aft.business.view.bean;

import java.math.BigDecimal;

/**
 * 金額類型，單位是分
 * @author xbcheng
 */
public class PriceType {
	private static final long serialVersionUID = 1L;
	public int priceValue = 0;
	

	public PriceType(int value) {
		super();
		this.priceValue = value;
	}
	
	public PriceType() {
		super();
		this.priceValue = 0;
	}
	
    /**
	 * 将int类型金额字段除以100转化为字符串
	 */
    public String getPriceValueForDisplay() {
         BigDecimal bigDecimal = new BigDecimal(this.priceValue);
         BigDecimal div = new BigDecimal(100);
         return bigDecimal.divide(div).toString();
    }
    
    /**-----------------int 类型的加减乘除******/
    
	/**
	 * int 加法
	 * @param value
	 */
	public void  add(int value) {
		this.priceValue += value;
	}

	/**
	 * int 减法
	 * @param value
	 */
	public void  subtract(int value) {
		this.priceValue -= value;
	}
	/**
	 * int 乘法
	 * @param value
	 */
	public void  multiply(int value) {
		this.priceValue *= value;
	}
	
//	public void  divide(int value) {
////		this.priceValue *= value;
//	}
    /**-----------------int 类型的加减乘除******/

	
    /**-----------------PriceType 类型的加减乘除******/
	/**
	 * PriceType 加法
	 * @param value
	 */
	public void  add(PriceType value) {
		this.priceValue += value.priceValue;
	}
	/**
	 * PriceType 减法
	 * @param value
	 */
	public void  subtract(PriceType value) {
		this.priceValue -= value.priceValue;
	}
	/**
	 * PriceType 乘法
	 * @param value
	 */
	public void  multiply(PriceType value) {
		this.priceValue *= value.priceValue;
	}
	
//	public void  divide(PriceType value) {
//		
//	}
    /**-----------------PriceType 类型的加减乘除******/

}
