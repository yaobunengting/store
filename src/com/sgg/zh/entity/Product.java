package com.sgg.zh.entity;

import java.util.Date;

/**
 * 商品实体
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
	private Integer is_hot = 0;	//是否热门	1:热门  0:否
	private String pdesc;
	private Integer pflag = 0;	//是否下架	1:下架  0:为下架
	//属于哪个分类
	private Category c;
	
	
	
}
