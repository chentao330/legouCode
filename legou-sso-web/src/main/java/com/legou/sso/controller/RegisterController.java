package com.legou.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbUser;
import com.legou.sso.service.RegisterService;

@Controller
public class RegisterController {

	@Autowired
	private RegisterService registerService;
		
	@RequestMapping("/page/register")
	public String toRegister() {
		return "register";
	}
	
	//检查用户名
	@RequestMapping("/user/check/{params}/{type}")
	@ResponseBody
	public LegouResult checkUser(@PathVariable String params,@PathVariable int type) {
		
		LegouResult legouResult=registerService.check(params,type);
		return legouResult;
	}
	
	//注册
	@RequestMapping("/user/register")
	@ResponseBody
	public LegouResult register(TbUser tbUser) {
		LegouResult legouResult=registerService.registerUser(tbUser);
		return legouResult;
	}
	
}
