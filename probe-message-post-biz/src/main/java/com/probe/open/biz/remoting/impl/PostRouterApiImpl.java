package com.probe.open.biz.remoting.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.probe.open.biz.remoting.PostRouterApi;
import com.probe.open.entity.OpenThirdInfo;
import com.probe.open.entity.PostRouter;
import com.probe.open.service.PostRouterService;

@Service("postRouterApiImpl")
public class PostRouterApiImpl implements PostRouterApi {

	@Autowired
	PostRouterService postRouterService;

	public boolean setPostRouter(String token, String probeSn) {

		OpenThirdInfo thirdInfo = postRouterService.getThirdInfo(token);

		if (thirdInfo != null) {
			PostRouter postRouter = new PostRouter();
			postRouter.setProbesn(probeSn);
			postRouter.setThirdid(thirdInfo.getId());
			postRouterService.insert(postRouter);
			return true;
		}
		
		return false;
	}

}
