package edu.xmu.test.blog.sample.session;

import com.google.common.base.Joiner;

import javax.persistence.criteria.Join;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by davywalker on 16/10/31.
 * 统一的同步cookie地址.
 * http://jump.usercenter.com:port
 */
public class UserCenterJumpServlet extends HttpServlet {
    SessionManager sessionManager = SessionManager.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    private void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 检查是否有登录态
        String fromUrl = req.getParameter(UserCenterConstants.FROM_URL_PARAM_KEY);
        String passUrl = req.getParameter(UserCenterConstants.PASS_URL_PARAM_KEY);
        String loginUrl = req.getParameter(UserCenterConstants.LOGIN_URL_PARAM_KEY);

        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if ("sid".equals(cookie.getName())) {
                String sid = cookie.getValue();
                if (sessionManager.isLogin(sid)) {
                    // 有登录态, 302到pass.mydomain.com域名下, 进行cookie同步
                    String syncUrl = buildUrlForSyncCookie(req, sid);
                    System.out.println("syncUrl=" + syncUrl);
                    resp.sendRedirect(syncUrl);
                    return;
                }
            }
        }
        // 无登录态, 302跳转到统一登录中心, 进行登录写cookie
        String loginFullUrl = buildUrlForLogin(req);
        System.out.println("loginFullUrl=" + loginFullUrl);
        resp.sendRedirect(loginFullUrl);
        return;
    }

    private String buildUrlForLogin(HttpServletRequest req) {
        String fromUrl = req.getParameter(UserCenterConstants.FROM_URL_PARAM_KEY);
        String passUrl = req.getParameter(UserCenterConstants.PASS_URL_PARAM_KEY);
        String loginUrl = req.getParameter(UserCenterConstants.LOGIN_URL_PARAM_KEY);
        String jumpUrl = req.getRequestURL().toString();
        String loginFullUrl = loginUrl + "?" + Joiner.on("&").join(UserCenterConstants.FROM_URL_PARAM_KEY + "=" + fromUrl, UserCenterConstants.PASS_URL_PARAM_KEY + "=" + passUrl, UserCenterConstants.JUMP_URL_PARAM_KEY + "=" + jumpUrl);
        return loginFullUrl;
    }

    private String buildUrlForSyncCookie(HttpServletRequest req, String sid) {
        String fromUrl = req.getParameter(UserCenterConstants.FROM_URL_PARAM_KEY);
        String passUrl = req.getParameter(UserCenterConstants.PASS_URL_PARAM_KEY);
        String loginUrl = req.getParameter(UserCenterConstants.LOGIN_URL_PARAM_KEY);
        String syncUrl = passUrl + "?" + Joiner.on("&").join(UserCenterConstants.FROM_URL_PARAM_KEY + "=" + fromUrl, UserCenterConstants.LOGIN_URL_PARAM_KEY + "=" + loginUrl, UserCenterConstants.SESSION_ID + "=" + sid);
        return syncUrl;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }
}
