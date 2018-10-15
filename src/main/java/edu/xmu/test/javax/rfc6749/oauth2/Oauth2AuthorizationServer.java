package edu.xmu.test.javax.rfc6749.oauth2;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

public class Oauth2AuthorizationServer {
	/**
	 * 在开放平台入驻的应用列表 {appKey:appSecret}
	 */
	Map<String, String> trustApps = Maps.newHashMap();

	/**
	 * {appKey:"accessToken,openId"}
	 */
	Map<String, String> accessTokens = Maps.newHashMap();

	/**
	 * 开放平台入驻应用: 默认不做应用与权限过滤
	 * 
	 * @param appKey
	 * @return
	 */
	public String applyAppSecret(String appKey) {
		String appSecret = UUID.randomUUID().toString();
		trustApps.put(appKey, appSecret);
		return appSecret;
	}

	public String generateAccessToken(String appKey, String appSecret, String openId) {
		if (trustApps.get(appKey).equals(appSecret)) {
			String accessToken = UUID.randomUUID().toString();
			accessTokens.put(appKey, accessToken + "," + openId);
			return accessToken;
		} else {
			return null;
		}

	}

	public String validateAccessToken(String appKey, String accessToken) {
		if (accessTokens.containsKey(appKey)) {
			String str = accessTokens.get(appKey);
			String[] split = StringUtils.split(str, ",");
			if (accessToken.equals(split[0])) {
				return split[1];
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

}
