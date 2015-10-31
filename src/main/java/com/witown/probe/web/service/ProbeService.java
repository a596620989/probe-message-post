package com.witown.probe.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.witown.dao.OpenThirdInfoMapper;
import com.witown.entity.OpenThirdInfo;

@Service
public class ProbeService {

	@Autowired
	private OpenThirdInfoMapper userMapper;

	public void setUserMapper(OpenThirdInfoMapper userMapper) {
	  this.userMapper = userMapper;
	}
	
	
	public OpenThirdInfo aas(){
		return userMapper.selectByPrimaryKey(1);
	}
}
