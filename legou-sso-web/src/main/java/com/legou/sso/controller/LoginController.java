package com.legou.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.utils.CookieUtils;
import com.legou.common.utils.LegouResult;
import com.legou.sso.service.LoginService;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@RequestMapping("/page/login")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping("/user/login")
	@ResponseBody
	public LegouResult login(String username,String password,HttpServletRequest request,HttpServletResponse response) {
		LegouResult legouResult=loginService.login(username,password);
		//判断是否登录成功
		if(legouResult.getStatus() == 200) {
			//获取token的数据并把token写入cookie客户端
			String token=legouResult.getData().toString();
			CookieUtils.setCookie(request, response, "token", token);
		}
		return legouResult;
	}
	
}
