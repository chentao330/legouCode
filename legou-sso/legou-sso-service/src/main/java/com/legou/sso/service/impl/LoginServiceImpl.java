package com.legou.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.legou.common.jedis.JedisClient;
import com.legou.common.utils.JsonUtils;
import com.legou.common.utils.LegouResult;
import com.legou.mapper.TbUserMapper;
import com.legou.pojo.TbUser;
import com.legou.pojo.TbUserExample;
import com.legou.pojo.TbUserExample.Criteria;
import com.legou.sso.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private TbUserMapper tbUserMapper;
	//需要把用户的信息放入Redis缓存中所以需要注入redis的类
	@Autowired
	private JedisClient JedisClient;
	//设置一个消亡时间
	private  final Integer DealTime=1800;
	
	@Override
	public LegouResult login(String username, String password) {
		//查询出是否有这个用户名和密码存在的用户
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		//设置查询用户名为这个的用户
		criteria.andUsernameEqualTo(username);
		//查询
		List<TbUser> list = tbUserMapper.selectByExample(example);
		//判断list是否为空有没有数据
		if(null==list || list.size()<=0) {
			TbUserExample example2=new TbUserExample();
			Criteria criteria2 = example2.createCriteria();
			criteria2.andPhoneEqualTo(username);
			list = tbUserMapper.selectByExample(example2);
			if(list==null || list.size()<=0) {
				return LegouResult.build(400, "用户名或密码错误");
			}
		}
		//如果不为空那就获取这个list
		//因为设定用户名不能重复，所以直接获取第一位就好了
		TbUser tbUser = list.get(0);
		//判断密码是否一致
		String pwd=DigestUtils.md5DigestAsHex(password.getBytes());
		//如果不一致则返回错误信息
		if (!pwd.equals(tbUser.getPassword())) {
			return LegouResult.build(400, "用户名或密码错误");
		}
		
		//错误的淘汰掉后，后面如果一致的话就可以放入redis缓存中
		//设置token的value为uuid
		String token=UUID.randomUUID().toString();
		//为安全性考虑设置密码为null
		tbUser.setPassword(null);
		//将tbuser放入redis
		JedisClient.set("SESSION:"+token, JsonUtils.objectToJson(tbUser));
		//设置它的过期时间
		JedisClient.expire("SESSION:"+token, DealTime);
		//返回token的数据
		return LegouResult.ok(token);
	}

}
