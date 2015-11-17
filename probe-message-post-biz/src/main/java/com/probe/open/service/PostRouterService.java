package com.probe.open.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.witown.domain.probe.OpenThirdInfo;
import com.witown.domain.probe.PostRouter;
import com.witown.remoting.service.RemotePostRouterService;

/**
 * @author eros
 *
 */
@Service
public class PostRouterService {
	
	@Autowired
	RemotePostRouterService remotePostRouterService;
	
	
//	@Autowired
//	private PostRouterApiImpl aaa;
	
	public String getToken(String probeSn){
		PostRouter postRouter = remotePostRouterService.getPostRouterByProbeSn(probeSn);
		
		if(postRouter == null){
			return null;
		}
		
		OpenThirdInfo thirdInfo = remotePostRouterService.getThirdInfoById(postRouter.getThirdId());
		
		if(thirdInfo == null){
			return null;
		}
		
		return thirdInfo.getToken();
	}
	
	public String getAddress(String probeSn){
		
		PostRouter postRouter = remotePostRouterService.getPostRouterByProbeSn(probeSn);
		
		if(postRouter == null){
			return null;
		}
		
		OpenThirdInfo thirdInfo = remotePostRouterService.getThirdInfoById(postRouter.getThirdId());
		
		if(thirdInfo == null){
			return null;
		}
		
		return thirdInfo.getUrl();
	}
	
	
	public OpenThirdInfo getThirdInfo(String token){
		
		return remotePostRouterService.getThirdInfoByToken(token);
	}
	
//	public void insert(PostRouter postRouter){
//		postRouter.setGmtcreated(new Date());
//		postRouter.setGmtmodified(new Date());
//		postRouterMapper.insert(postRouter);
//	}
	
//	public boolean isProbeExist(String probeSn){
//		PostRouter postRouter = remotePostRouterService.getPostRouterByProbeSn(probeSn);
//		if(postRouter == null){
//			return false;
//		}
//		return true;
//	}
}
