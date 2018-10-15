package edu.xmu.test.javax.rfc6749.oauth2;

public class Oauth2Client {
	private String appKey;
	private String appSecret;
	private Oauth2ResourceServer resourceServer;

	public Oauth2Client(String appKey, String appSecret) {
		super();
		this.appKey = appKey;
		this.appSecret = appSecret;
	}

	public String authRequest(Oauth2ResourceOwner resourceOwner) {
		return resourceOwner.grantAuthRequest(this);
	}

	public Oauth2Resource getResource(String accessToken) {
		return resourceServer.validateAccessToken(appKey, accessToken);
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public Oauth2ResourceServer getResourceServer() {
		return resourceServer;
	}

	public void setResourceServer(Oauth2ResourceServer resourceServer) {
		this.resourceServer = resourceServer;
	}

}
