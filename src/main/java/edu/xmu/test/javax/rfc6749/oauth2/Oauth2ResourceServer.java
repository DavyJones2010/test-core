package edu.xmu.test.javax.rfc6749.oauth2;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 资源服务器, 只对accessToken感知
 * 
 * @author davyjones
 *
 */
public class Oauth2ResourceServer {
	/**
	 * {OpenId:Oauth2Resource}
	 */
	Map<String, Oauth2Resource> resources = Maps.newHashMap();
	Oauth2AuthorizationServer oauth2AuthorizationServer;

	public Oauth2Resource validateAccessToken(String appKey, String accessToken) {
		String openId = oauth2AuthorizationServer.validateAccessToken(appKey, accessToken);
		if (null == openId) {
			return null;
		} else {
			return resources.get(openId);
		}
	}

	public void saveAuthResouce(String openId, Oauth2Resource oauth2Resource) {
		resources.put(openId, oauth2Resource);
	}

	public Oauth2AuthorizationServer getOauth2AuthorizationServer() {
		return oauth2AuthorizationServer;
	}

	public void setOauth2AuthorizationServer(Oauth2AuthorizationServer oauth2AuthorizationServer) {
		this.oauth2AuthorizationServer = oauth2AuthorizationServer;
	}

}
