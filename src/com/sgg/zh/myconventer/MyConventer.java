package com.sgg.zh.myconventer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.beanutils.Converter;

public class MyConventer implements Converter {

	@Override
	public Object convert(Class clazz, Object value) {
		/*
		 * ����1 Class clazz Ҫת�ɵ�����
		 * ����2 Object value ҳ���ϴ����ֵ
		 */
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate ldt = LocalDate.parse((String)value, dtf);
		
		
		return ldt;
	}

}
