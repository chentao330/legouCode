package com.legou.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.pojo.EasyUITreeNode;
import com.legou.service.TbItemCatService;

@Controller
public class TbItemCatController {
	
	@Autowired
	private TbItemCatService tbItemCatService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(name="id" ,defaultValue="0") long parentId){
		
		List<EasyUITreeNode> itemCatList = tbItemCatService.getItemCatList(parentId);
		
		return itemCatList;
	}
}
