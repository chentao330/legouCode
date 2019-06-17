package com.legou.content.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.legou.common.pojo.EasyUIDataGridResult;
import com.legou.common.utils.LegouResult;
import com.legou.content.service.ContentService;
import com.legou.mapper.TbContentMapper;
import com.legou.pojo.TbContent;
import com.legou.pojo.TbContentExample;
import com.legou.pojo.TbContentExample.Criteria;
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper tbContentMapper;
	@Override
	public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
		//设置分页助手
		PageHelper.startPage(page, rows);
		//查询出此Id对应的所有内容
		List<TbContent> list = tbContentMapper.selectByCatId(categoryId);
		
		EasyUIDataGridResult easyUIDataGridResult=new EasyUIDataGridResult();
		//设置easyUIDataGridResult的参数
		easyUIDataGridResult.setRows(list);
		//创建分页对象
		PageInfo<TbContent> pageInfo=new PageInfo<TbContent>(list);
		//设置总数
		easyUIDataGridResult.setTotal(pageInfo.getTotal());
		
		
		return easyUIDataGridResult;
	}
	@Override
	public LegouResult addContent(TbContent tbContent) {
		// TODO Auto-generated method stub
		//设置创建时间和更新时间
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		tbContentMapper.insert(tbContent);
		return LegouResult.ok();
	}
	@Override
	public LegouResult editContent(TbContent tbContent) {
		//先查询出老的内容
		TbContent oldContent = tbContentMapper.selectByPrimaryKey(tbContent.getId());
		//设置时间
		tbContent.setCreated(oldContent.getCreated());
		tbContent.setUpdated(new Date());
		
		//更新
		tbContentMapper.updateByPrimaryKey(tbContent);
		
		return LegouResult.ok();
	}
	@Override
	public LegouResult deleteContent(long ids) {
		tbContentMapper.deleteByPrimaryKey(ids);
		return LegouResult.ok();
	}
	@Override
	public List<TbContent> getDGGList(long cid) {
		
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		//创建查询条件
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = tbContentMapper.selectByExample(example);
		return list;
	}
	
}
