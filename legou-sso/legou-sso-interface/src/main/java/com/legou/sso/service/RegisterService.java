package com.legou.sso.service;

import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbUser;

public interface RegisterService {

	LegouResult check(String params, int type);

	LegouResult registerUser(TbUser tbUser);

}
