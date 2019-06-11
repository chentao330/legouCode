package com.legou.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.pojo.EasyUITreeNode;
import com.legou.common.utils.LegouResult;
import com.legou.content.service.ContentCatService;
import com.legou.pojo.TbContentCategory;
import com.legou.pojo.TbItemCat;

@Controller
public class ContentCatController {
	@Autowired
	private ContentCatService contentCatService;
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getCatList(@RequestParam(name="id",defaultValue="0") long parentid) {
		List<EasyUITreeNode> listNode=contentCatService.getCatList(parentid);
		return listNode;
	}
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public LegouResult addCat(long parentId, String name) {
		LegouResult legouResult=contentCatService.addCat(parentId,name);
		return legouResult;
	}
	@RequestMapping("/content/category/delete/")
	@ResponseBody
	public LegouResult deleteCat(long id) {
		LegouResult legouResult=contentCatService.deleteCat(id);
		return legouResult;
	}
	
	@RequestMapping("/content/category/update")
	@ResponseBody
	public LegouResult updateCat(long id,String name) {
		LegouResult legouResult=contentCatService.updateCat(id,name);
		
		return legouResult;
	}
}
