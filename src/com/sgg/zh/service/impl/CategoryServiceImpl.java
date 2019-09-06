package com.sgg.zh.service.impl;

import java.util.List;

import com.sgg.zh.dao.CategoryDao;
import com.sgg.zh.dao.impl.CategoryDaoImpl;
import com.sgg.zh.entity.Category;
import com.sgg.zh.service.CategoryService;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CategoryServiceImpl implements CategoryService {

	/**
	 * ��ѯ���еķ���
	 */
	@Override
	public List<Category> findAll() throws Exception {
		// 1.�������������
		CacheManager cm = CacheManager.create(CategoryDaoImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		System.out.println(cm);
		// 2.��ȡָ���Ļ���
		Cache cache = cm.getCache("categoryCache");

		// 3.ͨ�������ȡ����
		Element element = cache.get("clist");
		List<Category> list = null;
		// 4.�ж�����
		if (element == null) {
			// elementΪ�յĻ�,����Ҫ�����ݿ��л�ȡ
			CategoryDao cd = new CategoryDaoImpl();
			list = cd.findAll();
			// ��list���뻺��
			cache.put(new Element("clist", list));
			System.out.println("������û������,��ȥ���ݿ��л�ȡ");
		} else {
			// ֱ�ӷ���
			list = (List<Category>) element.getObjectValue();
			System.out.println("��������������");
		}

		return list;
	}

}
