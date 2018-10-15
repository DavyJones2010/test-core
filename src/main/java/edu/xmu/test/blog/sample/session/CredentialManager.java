package edu.xmu.test.blog.sample.session;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by davywalker on 16/10/31.
 * 单例, 模拟统一的用户校验服务
 */
public class CredentialManager {
    private Map<String, String> map = Maps.newHashMap();
    private static CredentialManager credentialManager = new CredentialManager();

    private CredentialManager() {
        map.put("davy", "hello");
        map.put("tom", "world");
        map.put("admin", "1234");
    }

    public boolean checkCredential(String username, String password) {
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return false;
        }
        String s = map.get(username);
        if (StringUtils.isNotBlank(s) && s.equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public static CredentialManager getInstance() {
        return credentialManager;
    }
}
