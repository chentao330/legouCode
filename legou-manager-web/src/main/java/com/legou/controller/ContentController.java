package com.legou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.pojo.EasyUIDataGridResult;
import com.legou.common.utils.LegouResult;
import com.legou.content.service.ContentService;
import com.legou.pojo.TbContent;

@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(long categoryId,int page,int rows) {		
		return contentService.getContentList(categoryId,page,rows);
	}
	
	@RequestMapping("/content/save")
	@ResponseBody
	public LegouResult addContent(TbContent tbContent) {
		LegouResult legouResult=contentService.addContent(tbContent);
		return legouResult;
	}
	
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public LegouResult editContent(TbContent tbContent) {
		LegouResult legouResult=contentService.editContent(tbContent);
		return legouResult;
	}
	@RequestMapping("/content/delete")
	@ResponseBody
	public LegouResult deleteContent(long ids) {
		LegouResult legouResult=contentService.deleteContent(ids);
		return legouResult;
	}
}
