package com.probe.open.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.probe.open.dao.OpenThirdInfoMapper;
import com.probe.open.entity.OpenThirdInfo;

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
