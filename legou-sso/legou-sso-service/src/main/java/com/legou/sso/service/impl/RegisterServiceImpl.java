package com.legou.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.legou.common.utils.LegouResult;
import com.legou.mapper.TbUserMapper;
import com.legou.pojo.TbUser;
import com.legou.pojo.TbUserExample;
import com.legou.pojo.TbUserExample.Criteria;
import com.legou.sso.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private TbUserMapper tbUserMapper;

	@Override
	public LegouResult check(String params, int type) {
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		//1用户名  2  手机号      3email 
		if (type==1) {
			criteria.andUsernameEqualTo(params);
		}else if (type==2) {
			criteria.andPhoneEqualTo(params);
		}else if (type==3) {
			criteria.andEmailEqualTo(params);
		}else {
			return LegouResult.build(400, "参数类型不正确");
		}
		
		List<TbUser> userList = tbUserMapper.selectByExample(example);
		
		if(userList !=null && userList.size()>0) {
			return LegouResult.ok(false);
		}
		return LegouResult.ok(true);
	}

	@Override
	public LegouResult registerUser(TbUser tbUser) {
		//判断用户名密码电话是否为空
		if (StringUtils.isBlank(tbUser.getUsername()) || StringUtils.isBlank(tbUser.getPassword())|| StringUtils.isBlank(tbUser.getPhone())) {
			return LegouResult.build(400, "用户名/密码/手机号不能为空！！请检查完重新输入");
		}
		//二次检查确保安全性
		LegouResult result = check(tbUser.getUsername(), 1);
		//如果存在的话
		if(!(boolean) result.getData()) {
			return LegouResult.build(400, "用户名已存在请重新输入！");
		}
		result = check(tbUser.getPhone(), 2);
		if (!(boolean)result.getData()) {
			return LegouResult.build(400, "手机号已存在");
		}
		//补全信息
		tbUser.setCreated(new Date());
		tbUser.setUpdated(new Date());
		//用MD5方式加密密码
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
		tbUser.setPassword(md5DigestAsHex);
		tbUserMapper.insert(tbUser);
		return LegouResult.ok();
	}	
}
