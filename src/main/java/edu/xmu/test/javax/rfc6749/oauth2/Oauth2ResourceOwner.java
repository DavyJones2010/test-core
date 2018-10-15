package edu.xmu.test.javax.rfc6749.oauth2;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 终端用户
 */
public class Oauth2ResourceOwner {
	private String openId;
	private Oauth2ResourceServer oauth2ResourceServer;
	private Oauth2AuthorizationServer oauth2AuthorizationServer;

	List<String> oauth2ClientWhiteList = Lists.newArrayList("weixin", "qq");

	public Oauth2ResourceOwner(String openId) {
		super();
		this.openId = openId;
	}

	/**
	 * @param oauth2Client
	 * @return accessToken
	 */
	public String grantAuthRequest(Oauth2Client oauth2Client) {
		if (oauth2ClientWhiteList.contains(oauth2Client.getAppKey())) {
			String accessToken = oauth2AuthorizationServer.generateAccessToken(oauth2Client.getAppKey(),
					oauth2Client.getAppSecret(), openId);
			return accessToken;
		} else {
			return null;
		}
	}

	public void saveAuthResource(Oauth2Resource oauth2Resource) {
		oauth2ResourceServer.saveAuthResouce(openId, oauth2Resource);
	}

	public Oauth2AuthorizationServer getOauth2AuthorizationServer() {
		return oauth2AuthorizationServer;
	}

	public void setOauth2AuthorizationServer(Oauth2AuthorizationServer oauth2AuthorizationServer) {
		this.oauth2AuthorizationServer = oauth2AuthorizationServer;
	}

	public Oauth2ResourceServer getOauth2ResourceServer() {
		return oauth2ResourceServer;
	}

	public void setOauth2ResourceServer(Oauth2ResourceServer oauth2ResourceServer) {
		this.oauth2ResourceServer = oauth2ResourceServer;
	}

}
