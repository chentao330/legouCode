package com.legou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.legou.common.pojo.EasyUIDataGridResult;
import com.legou.mapper.TbItemMapper;
import com.legou.pojo.TbItem;
import com.legou.pojo.TbItemExample;
import com.legou.service.TbItemService;

@Service
public class TbItemServiceImpl implements TbItemService{
	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Override
	public TbItem getTbItemById(Long id) {
		TbItem item = tbItemMapper.selectByPrimaryKey(id);
		return item;
	}

	@Override
	public EasyUIDataGridResult getDataGridResult(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//创建一个TbItemExample对象
		TbItemExample example=new TbItemExample();
		//执行查询
		List<TbItem> list = tbItemMapper.selectByExample(example);
		EasyUIDataGridResult result=new EasyUIDataGridResult();
		//设置result里rows的数据
		result.setRows(list);
		//创建一个pageinfo对象
		PageInfo<TbItem> info=new PageInfo<TbItem>(list);
		//设置result的总数
		result.setTotal(info.getTotal());
		return result;
	}

}
