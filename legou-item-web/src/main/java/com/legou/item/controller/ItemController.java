package com.legou.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legou.item.pojo.Item;
import com.legou.pojo.TbItem;
import com.legou.pojo.TbItemDesc;
import com.legou.service.TbItemService;

@Controller
public class ItemController {

	@Autowired
	private TbItemService tbItemService;
	
	@RequestMapping("/item/{itemid}")
	public String getItemInfo(@PathVariable long itemid,Model model) {
		//获取tbItem的数据信息
		TbItem tbItem = tbItemService.geTbItem(itemid);
		Item item=new Item(tbItem);
		//获取tbItemDesc的内容
		TbItemDesc itemDesc = tbItemService.geTbItemDesc(itemid);
		
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		
		return "item";
	}
	
}
