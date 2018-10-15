package edu.xmu.test.blog.sample.session;

import com.google.common.base.Joiner;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by davywalker on 16/10/31.
 * 需要有登录态的具体业务站点
 * http://cart.mydomain.com:port
 */
public class MyDomainCartServlet extends HttpServlet {
    SessionManager sessionManager = SessionManager.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    private void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 检查是否有登录态
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (UserCenterConstants.SESSION_ID.equals(cookie.getName())) {
                String sid = cookie.getValue();
                if (sessionManager.isLogin(sid)) {
                    // 有登录态, 直接显示登录用户名
                    resp.getWriter().print("username=" + sessionManager.getLoginInfo(sid) + " you can continue shopping");
                    return;
                }
            }
        }
        // 无登录态, 需要302跳转到jump.usercenter.com域名下进行cookie同步操作
        System.out.println("requestURI=" + req.getRequestURI());
        String redirectUrl = buildUrl(req);
        System.out.println("redirectUrl=" + redirectUrl);
        resp.sendRedirect(redirectUrl);
        return;
    }

    private String buildUrl(HttpServletRequest req) {
        String jumpUrl = req.getParameter(UserCenterConstants.JUMP_URL_PARAM_KEY);
        String passUrl = req.getParameter(UserCenterConstants.PASS_URL_PARAM_KEY);
        String loginUrl = req.getParameter(UserCenterConstants.LOGIN_URL_PARAM_KEY);
        String fromUrl = req.getRequestURL().toString();
        String url = jumpUrl + "?" + Joiner.on("&").join(UserCenterConstants.FROM_URL_PARAM_KEY + "=" + fromUrl, UserCenterConstants.PASS_URL_PARAM_KEY + "=" + passUrl, UserCenterConstants.LOGIN_URL_PARAM_KEY + "=" + loginUrl);
        return url;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }
}
