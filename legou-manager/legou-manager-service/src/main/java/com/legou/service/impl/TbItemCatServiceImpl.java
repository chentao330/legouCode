package com.legou.service.impl;

import java.util.ArrayList;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.common.pojo.EasyUITreeNode;
import com.legou.mapper.TbItemCatMapper;
import com.legou.pojo.TbItemCat;
import com.legou.pojo.TbItemCatExample;
import com.legou.pojo.TbItemCatExample.Criteria;
import com.legou.service.TbItemCatService;

@Service
public class TbItemCatServiceImpl implements TbItemCatService {
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		//创建example对象查询商品类目
		TbItemCatExample example=new TbItemCatExample();
		//创建设置查询对象criteria
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		//创建EasyUITreeNode list对象
		List<EasyUITreeNode> resulTreeNodes=new ArrayList<EasyUITreeNode>();
		
		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode etNode=new EasyUITreeNode();
			etNode.setId(tbItemCat.getId());
			etNode.setText(tbItemCat.getName());
			etNode.setState(tbItemCat.getIsParent()?"closed":"open");
			resulTreeNodes.add(etNode);
		}
		
		return resulTreeNodes;
	}
	
}
