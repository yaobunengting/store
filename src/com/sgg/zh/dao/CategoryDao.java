package com.sgg.zh.dao;

import java.util.List;

import com.sgg.zh.entity.Category;

public interface CategoryDao {

	List<Category> findAll() throws Exception;

}
