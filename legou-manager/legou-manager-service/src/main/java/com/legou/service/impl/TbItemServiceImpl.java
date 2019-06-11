package com.legou.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.legou.common.pojo.EasyUIDataGridResult;
import com.legou.common.utils.IDUtils;
import com.legou.common.utils.LegouResult;
import com.legou.mapper.TbItemDescMapper;
import com.legou.mapper.TbItemMapper;
import com.legou.pojo.TbItem;
import com.legou.pojo.TbItemDesc;
import com.legou.pojo.TbItemExample;
import com.legou.service.TbItemService;

@Service
public class TbItemServiceImpl implements TbItemService{
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Override
	public Map<String ,Object> getTbItemById(Long id) {

		TbItem item = tbItemMapper.selectByPrimaryKey(id);
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);

		Map<String ,Object> map=new HashMap<String ,Object>();
		map.put("item", item);
		map.put("desc", tbItemDesc.getItemDesc());
		return map;
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

	@Override
	public LegouResult addItemAndDesc(TbItem tbItem, String desc) {
		//创建一个ID编号，编号为时间轴
		long genItemId = IDUtils.genItemId();

		//给TBItem设置值
		tbItem.setId(genItemId);
		tbItem.setStatus((byte) 1);
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		//添加商品
		tbItemMapper.insert(tbItem);

		//添加商品描述
		TbItemDesc tbItemDesc=new TbItemDesc();
		tbItemDesc.setItemId(genItemId);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		tbItemDescMapper.insert(tbItemDesc);

		return LegouResult.ok();
	}

	@Override
	public LegouResult deleteItemAndDesc(long ids) {
		//删除此Id对应desc表的数据
		tbItemDescMapper.deleteByPrimaryKey(ids);

		//删除此Id对应item表的数据
		tbItemMapper.deleteByPrimaryKey(ids);

		return LegouResult.ok();
	}

	@Override
	public LegouResult instock(long ids) {
		//查询这条数据
		TbItem tbitem = tbItemMapper.selectByPrimaryKey(ids);
		//修改此物品的状态为2则为下架
		tbitem.setStatus((byte) 2);
		tbItemMapper.updateByPrimaryKey(tbitem);

		return LegouResult.ok();
	}

	@Override
	public LegouResult reshelf(long ids) {
		//查询这条数据
		TbItem tbitem = tbItemMapper.selectByPrimaryKey(ids);
		//修改此物品的状态为1则为上架
		tbitem.setStatus((byte) 1);
		tbItemMapper.updateByPrimaryKey(tbitem);

		return LegouResult.ok();
	}

	@Override
	public LegouResult updateItemAndDesc(Map<String, Object> map) {
		
		TbItem tbItem = (TbItem) map.get("tbItem");
		TbItem t1=tbItemMapper.selectByPrimaryKey(tbItem.getId());
		tbItem.setStatus((byte)1);
		tbItem.setCreated(t1.getCreated());
		tbItem.setUpdated(new Date());
		tbItemMapper.updateByPrimaryKey(tbItem);
		
		
		String desc=(String)map.get("desc");
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(tbItem.getId());
		tbItemDesc.setUpdated(new Date());
		tbItemDesc.setItemDesc(desc);
		
		tbItemDescMapper.updateByPrimaryKey(tbItemDesc);	
		
		
		return LegouResult.ok();
	}

	@Override
	public LegouResult getDesc(long id) {
		// TODO Auto-generated method stub
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);
		
		return LegouResult.ok(tbItemDesc);
	}

}
