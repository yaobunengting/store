package com.sgg.zh.entity;

import java.util.Date;

/**
 * ��Ʒʵ��
 * @author Administrator
 *
 */
public class Product {
	/**
	 * `pid` VARCHAR(32) NOT NULL,
  `pname` VARCHAR(50) DEFAULT NULL,
  `market_price` DOUBLE DEFAULT NULL,
  `shop_price` DOUBLE DEFAULT NULL,
  `pimage` VARCHAR(200) DEFAULT NULL,
  `pdate` DATE DEFAULT NULL,
  `is_hot` INT(11) DEFAULT NULL,
  `pdesc` VARCHAR(255) DEFAULT NULL,
  `pflag` INT(11) DEFAULT NULL,
  `cid` VARCHAR(32) DEFAULT NULL,
	 */
	
	private String pid;
	private String pname;
	private Double market_price;
	private Double shop_price;
	private String pimage;
	private Date pdate;
	private Integer is_hot = 0;	//�Ƿ�����	1:����  0:��
	private String pdesc;
	private Integer pflag = 0;	//�Ƿ��¼�	1:�¼�  0:Ϊ�¼�
	//�����ĸ�����
	private Category c;
	
	
	
}
