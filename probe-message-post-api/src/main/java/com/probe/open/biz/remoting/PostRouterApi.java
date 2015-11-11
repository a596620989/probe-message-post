package com.probe.open.biz.remoting;

public interface PostRouterApi {
	

	/**
	 * 标识探针为某第三方所有
	 * 验证工作(如第三方是否具有该探针的所有权)由上层业务层提供
	 * @param thirdId
	 * @param probeSn
	 * @return
	 */
	public boolean setPostRouter(Integer thirdId, String probeSn);

}
