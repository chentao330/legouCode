package com.legou.controller;

import java.util.HashMap;
import java.util.Map;

import javax.activation.MailcapCommandMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.pojo.EasyUIDataGridResult;
import com.legou.common.utils.JsonUtils;
import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbItem;
import com.legou.service.TbItemService;

@Controller
public class TbItemController {
	
	@Autowired
	private TbItemService tbItemService;
	
//	@RequestMapping("/item/{itemid}")
//	@ResponseBody
//	public TbItem getItemById(@PathVariable Long itemid) {
//		TbItem item = tbItemService.getTbItemById(itemid);
//		return item;
//	}
	
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getDataGridResult(int page,int rows) {
		return tbItemService.getDataGridResult(page, rows);
	}
	
	@RequestMapping("/item/save")
	@ResponseBody
	public LegouResult addItemAndDesc(TbItem tbItem,String desc) {
		LegouResult legouResult=tbItemService.addItemAndDesc(tbItem,desc);
		return legouResult;
	}
	
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public LegouResult deleteItemAndDesc(long[] ids) {
		LegouResult legouResult=null;
		for (long l : ids) {
			legouResult= tbItemService.deleteItemAndDesc(l);
		}
		return legouResult;
	}
	
	@RequestMapping("/rest/item/update")
	@ResponseBody
	public LegouResult updateItemAndDesc(TbItem tbItem,String desc) {
		Map<String , Object> map=new HashMap<String, Object>();
		map.put("tbItem", tbItem);
		map.put("desc", desc);
		
		LegouResult legouResult=tbItemService.updateItemAndDesc(map);
		return legouResult;
	}
	
	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public LegouResult instock(long[] ids) {
		LegouResult legouResult=null;
		for (long l : ids) {
			legouResult= tbItemService.instock(l);
		}
		return legouResult;
	}
	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public LegouResult reshelf(long[] ids) {
		LegouResult legouResult=null;
		for (long l : ids) {
			legouResult= tbItemService.reshelf(l);
		}
		return legouResult;
	}
	@RequestMapping("/rest/item/query/item/desc/{id}")
	@ResponseBody
	public LegouResult getDesc(@PathVariable long id) {
		LegouResult legouResult=tbItemService.getDesc(id);
		
		return legouResult;
	}
}
