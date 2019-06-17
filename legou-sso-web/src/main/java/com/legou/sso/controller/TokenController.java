package com.legou.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.utils.LegouResult;
import com.legou.sso.service.TokenService;

@Controller
public class TokenController {
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping("/user/token/{token}")
	@ResponseBody
	public Object getTokenObj(@PathVariable String token, String callback) {
		LegouResult legouResult = tokenService.getTokenObj(token);
		//响应结果之前，判断是否为jsonp请求
		if (StringUtils.isNotBlank(callback)) {
			//把结果封装成一个js语句响应
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(legouResult);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return legouResult;
	}
}
