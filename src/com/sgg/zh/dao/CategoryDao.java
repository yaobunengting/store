package com.sgg.zh.dao;

import java.util.List;

import com.sgg.zh.entity.Category;

public interface CategoryDao {

	List<Category> findAll() throws Exception;

	void add(Category category) throws Exception;

	Category getById(String cid) throws Exception;

	void update(Category c) throws Exception;

	void delete(String cid) throws Exception;

}
