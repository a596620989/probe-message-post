package com.probe.open.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.probe.open.biz.remoting.impl.PostRouterApiImpl;
import com.probe.open.dao.OpenThirdInfoMapper;
import com.probe.open.dao.PostRouterMapper;
import com.probe.open.entity.OpenThirdInfo;
import com.probe.open.entity.PostRouter;
import com.probe.open.entity.PostRouterExample;

@Service
public class PostRouterService {

	@Autowired
	private OpenThirdInfoMapper openThirdInfoMapper;
	
	@Autowired
	private PostRouterMapper postRouterMapper;
	
//	@Autowired
//	private PostRouterApiImpl aaa;
	
	public String getToken(String probeSn){
		PostRouter postRouter = getPostRouter(probeSn);
		
		if(postRouter == null){
			return null;
		}
		
		OpenThirdInfo thirdInfo = (OpenThirdInfo) openThirdInfoMapper.selectByPrimaryKey(postRouter.getThirdid());
		
		if(thirdInfo == null){
			return null;
		}
		
		return thirdInfo.getToken();
	}
	
	public String getAddress(String probeSn){
		
		PostRouter postRouter = getPostRouter(probeSn);
		
		if(postRouter == null){
			return null;
		}
		
		OpenThirdInfo thirdInfo = (OpenThirdInfo) openThirdInfoMapper.selectByPrimaryKey(postRouter.getThirdid());
		
		if(thirdInfo == null){
			return null;
		}
		
		return thirdInfo.getUrl();
	}
	
	private PostRouter getPostRouter(String probeSn){
		PostRouterExample example = new PostRouterExample();
		example.createCriteria().andProbesnEqualTo(probeSn);
		List<PostRouter> list = postRouterMapper.selectByExample(example);	
		
		if(list.size() == 0){
			return null;
		}
		
		return list.get(0);
	}
	
	public void insert(PostRouter postRouter){
		postRouter.setGmtcreated(new Date());
		postRouter.setGmtmodified(new Date());
		postRouterMapper.insert(postRouter);
	}
}
