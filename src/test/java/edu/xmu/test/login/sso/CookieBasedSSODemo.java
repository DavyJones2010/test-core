package edu.xmu.test.login.sso;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 基础的单点登陆示例. 基于SsoSource与SsoTarget互信模式.
 * 互信模式有两种:
 * 1. SsoTarget掌握SsoSource引用, 调用SsoSource方法. <br/>
 * 2. SsoTarget与SsoSource共享cookie/token存储, 无需与SsoSource通信.(一般不会这么做)
 * 缺点有两个:
 * 1. Cookie不安全.<br/>
 * 2. 不能跨域访问.
 * Created by davywalker on 16/9/24.
 */
public class CookieBasedSSODemo {
    /**
     * SSO单点登陆源系统. 可以发起单点登陆.
     */
    static interface SsoSource {
        public String issueSsoCookie(String username, String password);

        public String validateSsoCookie(String ssoCookie);
    }

    /**
     * SSO单点登陆目标系统. 可以被单点登陆.
     */
    static interface SsoTarget {
        public void addSsoSource(SsoSource ssoSource);

        public void removeSsoSource(SsoSource ssoSource);

        public String ssoLogin(String ssoCookie);
    }

    static class UserAgent {
        String cookie;
    }

    static class SystemA implements SsoSource {
        Map<String, String> map = Maps.newHashMap();

        public String issueSsoCookie(String username, String password) {
            if (validate(username, password)) {
                String ssoCookie = UUID.randomUUID().toString();
                map.put(ssoCookie, username);
                return ssoCookie;
            }
            return null;
        }

        private boolean validate(String username, String password) {
            if ("hello".equals(username) && "world".equals(password)) {
                return true;
            }
            return false;
        }

        @Override
        public String validateSsoCookie(String ssoCookie) {
            return map.get(ssoCookie);
        }
    }

    static class SystemB implements SsoTarget {
        List<SsoSource> list = Lists.newArrayList();

        @Override
        public void addSsoSource(SsoSource ssoSource) {
            list.add(ssoSource);
        }

        @Override
        public void removeSsoSource(SsoSource ssoSource) {
            list.remove(ssoSource);
        }

        @Override
        public String ssoLogin(String ssoCookie) {
            String username = null;
            for (SsoSource ssoSource : list) {
                if (null != (username = ssoSource.validateSsoCookie(ssoCookie))) {
                    return username;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        SsoSource systemA = new SystemA();
        SsoTarget systemB = new SystemB();
        systemB.addSsoSource(systemA);
        String ssoCookie = systemA.issueSsoCookie("hello", "world");
        String username = systemB.ssoLogin(ssoCookie);
        System.out.println("SystemB login succeed. username=" + username);
    }
}
