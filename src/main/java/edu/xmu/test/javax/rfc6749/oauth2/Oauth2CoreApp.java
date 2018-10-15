package edu.xmu.test.javax.rfc6749.oauth2;

public class Oauth2CoreApp {

	public static void main(String[] args) {
		String appKey = "weixin";
		String appKey2 = "dummy_weixin";
		Oauth2AuthorizationServer authorizationServer = new Oauth2AuthorizationServer();
		String appSecret = authorizationServer.applyAppSecret(appKey);
		Oauth2Client client = new Oauth2Client(appKey, appSecret);

		Oauth2ResourceServer resourceServer = new Oauth2ResourceServer();
		resourceServer.setOauth2AuthorizationServer(authorizationServer);

		Oauth2ResourceOwner resourceOwner = new Oauth2ResourceOwner("hanting");
		resourceOwner.setOauth2ResourceServer(resourceServer);
		resourceOwner.setOauth2AuthorizationServer(authorizationServer);
		Oauth2Resource oauth2Resource = new Oauth2Resource("1", "hanting's pic1");
		resourceOwner.saveAuthResource(oauth2Resource);

		client.setResourceServer(resourceServer);

		String accessToken = client.authRequest(resourceOwner);
		Oauth2Resource resource = client.getResource(accessToken);
		System.out.println(resource);

		client = new Oauth2Client(appKey2, appSecret);
		client.setResourceServer(resourceServer);
		accessToken = client.authRequest(resourceOwner);
		System.out.println(accessToken);
	}
}
