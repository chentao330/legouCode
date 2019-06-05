package com.legou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

//页面显示
@Controller
public class PageController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/{page}")
	public String pageShow(@PathVariable String page) {
		return page;
	}
}
