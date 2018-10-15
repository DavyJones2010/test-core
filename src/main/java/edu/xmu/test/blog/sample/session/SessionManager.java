package edu.xmu.test.blog.sample.session;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.UUID;

/**
 * Created by davywalker on 16/10/31.
 * 单例, 模拟统一的session管理服务
 */
public class SessionManager {
    private Map<String, String> sidToUserNameMap = Maps.newHashMap();

    private static SessionManager sessionManager = new SessionManager();

    private SessionManager() {
    }

    public boolean isLogin(String sid) {
        return sidToUserNameMap.containsKey(sid);
    }

    public String getLoginInfo(String sid) {
        return sidToUserNameMap.get(sid);
    }

    public String login(String username) {
        String sid = StringUtils.replace(UUID.randomUUID().toString(), "-", "");
        sidToUserNameMap.put(sid, username);
        return sid;
    }

    public void logout(String sid) {
        if (StringUtils.isBlank(sid)) {
            return;
        }
        sidToUserNameMap.remove(sid);
    }

    public static SessionManager getInstance() {
        return sessionManager;
    }
}
