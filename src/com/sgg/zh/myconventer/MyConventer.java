package com.sgg.zh.myconventer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.beanutils.Converter;

public class MyConventer implements Converter {

	@Override
	public Object convert(Class clazz, Object value) {
		/*
		 * 参数1 Class clazz 要转成的类型
		 * 参数2 Object value 页面上传入的值
		 */
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate ldt = LocalDate.parse((String)value, dtf);
		
		
		return ldt;
	}

}
