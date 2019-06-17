package com.legou.search.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.legou.common.pojo.SearchResult;
import com.legou.search.service.SearchService;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/search")
	public String search(String keyword,@RequestParam(defaultValue="1")Integer page,Model model) throws Exception {
		//设置编码为utf-8解决编码错误
		keyword=new String(keyword.getBytes("ISO-8859-1"),"utf-8");
		//判断page传过来的值是不是为0
		if (page==0) page=1;
		int rows =60;
		
		SearchResult searchResult = searchService.search(keyword, page, rows);
		
		//页面需求：page totalPages recourdCount query itemList
		model.addAttribute("page", page);
		model.addAttribute("totalPages", searchResult.getTotalPages());
		model.addAttribute("recourdCount",searchResult.getRecordCount());
		model.addAttribute("query", keyword);
		model.addAttribute("itemList", searchResult.getItemList());
		
		
		return "search";
	}
	
}
