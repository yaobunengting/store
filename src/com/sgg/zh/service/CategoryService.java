package com.sgg.zh.service;

import java.util.List;

import com.sgg.zh.Entity.Category;

public interface CategoryService {

	List<Category> findAll() throws Exception;

}
