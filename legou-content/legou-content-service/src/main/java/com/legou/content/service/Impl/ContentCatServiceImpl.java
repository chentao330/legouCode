package com.legou.content.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.common.pojo.EasyUITreeNode;
import com.legou.common.utils.LegouResult;
import com.legou.content.service.ContentCatService;
import com.legou.mapper.TbContentCategoryMapper;
import com.legou.pojo.TbContentCategory;
import com.legou.pojo.TbContentCategoryExample;
import com.legou.pojo.TbContentCategoryExample.Criteria;

import net.sf.jsqlparser.statement.select.Select;
@Service
public class ContentCatServiceImpl implements ContentCatService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper; 
	@Override
	public List<EasyUITreeNode> getCatList(long parentid) {
		//创建example对象
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentid);
		//查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		
		List<EasyUITreeNode> treelist=new ArrayList<EasyUITreeNode>();
		for (TbContentCategory tcc : list) {
			EasyUITreeNode etn=new EasyUITreeNode();
			etn.setId(tcc.getId());
			etn.setText(tcc.getName());
			etn.setState(tcc.getIsParent()?"closed":"open");
			treelist.add(etn);
		}
		
		
		return treelist;
	}
	@Override
	public LegouResult addCat(long parentId, String name) {
		TbContentCategory tbContentCategory=new TbContentCategory();
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setName(name);
		
		tbContentCategory.setStatus(1);
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setIsParent(false);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		contentCategoryMapper.insert(tbContentCategory);
		
		TbContentCategory parent= contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()) {
			parent.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		return LegouResult.ok(tbContentCategory);
	}
	
	@Override
	public LegouResult deleteCat(long id) {
		/**1.查询这个是不是目录
			2.如果不是目录
				将它的状态改为2
			3.如果是目录
				则判断他下面有没有子文件，如果有则把子文件先改成2，在把目录改为2
				如果没有子文件，则把自己改为2
		*/	
		//查询这个目录是不是父目录
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		//判断是否为目录
		if (!contentCategory.getIsParent()) {
			contentCategory.setStatus(2);
			contentCategory.setUpdated(new Date());
			contentCategoryMapper.updateByPrimaryKey(contentCategory);
			return LegouResult.ok();
		}else {
			//判断有没有子文件
			List<TbContentCategory> list = contentCategoryMapper.selectByParentId(id,1);
			if (null==list) {
				//如果没有子文件的话就把自己改为2
				contentCategory.setStatus(2);
				contentCategory.setUpdated(new Date());
				contentCategoryMapper.updateByPrimaryKey(contentCategory);
				return LegouResult.ok();
			}else {
				//循环遍历修改子文件的状态为2
				for (TbContentCategory tbContentCategory : list) {
					tbContentCategory.setStatus(2);
					tbContentCategory.setUpdated(new Date());
					contentCategoryMapper.updateByPrimaryKey(tbContentCategory);
				}
				contentCategory.setStatus(2);
				contentCategory.setUpdated(new Date());
				contentCategoryMapper.updateByPrimaryKey(contentCategory);
				return LegouResult.ok();
			}
		}
		
	}
	@Override
	public LegouResult updateCat(long id, String name) {
		//根据ID查出数据
		TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		//设置name和更新时间
		tbContentCategory.setName(name);
		tbContentCategory.setUpdated(new Date());
		contentCategoryMapper.updateByPrimaryKey(tbContentCategory);
		return LegouResult.ok();
	}
	
	

}
