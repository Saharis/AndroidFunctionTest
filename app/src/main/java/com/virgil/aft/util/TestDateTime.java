package com.virgil.aft.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TestDateTime {
    public static String st_strin="content-st_strin";
    public static String st_strinNull;
    public String nor;
    public String norNull;
    public String norini="content-norini";
public static void main(String[] args){
//	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	long a=1398849663;
//	a=1398310472*1000;
//	System.out.println(simpleDateFormat.format(new Date(a)));
//	LogUtil.printlnInConsle(c("yyyy-MM-dd"));
	TestDateTime st=new TestDateTime();
	st.assgined();
}

	public static String c(String paramString)
	{
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString, Locale.getDefault());
		Date localDate = new Date();
		String str = localSimpleDateFormat.format(localDate);
		return str;
	}
	public void assgined(){
		this.nor="content-nor";
	}
}
