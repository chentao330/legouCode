package com.legou.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.legou.common.utils.CookieUtils;
import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbUser;
import com.legou.sso.service.TokenService;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private TokenService tokenService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//从cookie中取token
		String token=CookieUtils.getCookieValue(request, "token");
		//判断token是否为空，如果为空，直接放行
		if (StringUtils.isBlank(token)) {
			return true;
		}
		//不为空的话获取token
		LegouResult legouResult = tokenService.getTokenObj(token);
		//如果状态不为200的话直接放行
		if (legouResult.getStatus()!=200) {
			return true;
		}
		//如果满足状态为200的话
		//获取用户信息
		TbUser tbUser =(TbUser) legouResult.getData();
		//将用户的信息保存在request里
		request.setAttribute("user", tbUser);
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
