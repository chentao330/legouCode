package com.legou.sso.service;

import com.legou.common.utils.LegouResult;

public interface TokenService {
	LegouResult getTokenObj(String token);
}
