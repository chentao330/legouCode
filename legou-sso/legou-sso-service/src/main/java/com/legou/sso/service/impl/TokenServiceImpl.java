package com.legou.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.common.jedis.JedisClient;
import com.legou.common.utils.JsonUtils;
import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbUser;
import com.legou.sso.service.TokenService;
@Service
public class TokenServiceImpl implements TokenService {
	@Autowired
	private JedisClient jedisClient;
	@Override
	public LegouResult getTokenObj(String token) {
		//从redis里获取对象
		String json = jedisClient.get("SESSION:"+token);
		//判断json是否为空
		if (StringUtils.isBlank(json)) {
			return LegouResult.build(400, "用户信息已过期，请重新登录！");
		}
		//设置过期时间
		jedisClient.expire("SESSION:"+token, 1800);
		//把json转为User对象 
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		
		return LegouResult.ok(user);
	}

}
