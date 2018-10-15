package edu.xmu.test.blog.sample.session;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by davywalker on 16/10/31.
 * 统一的登录中心.
 * http://login.usercenter.com:port
 */
public class UserCenterLoginServlet extends HttpServlet {
    CredentialManager credentialManager = CredentialManager.getInstance();
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
                    // 有登录态; 如果from为空, 直接显示登录用户名; 如果from非空, 跳转到from页面
                    String fromUrl = req.getParameter(UserCenterConstants.FROM_URL_PARAM_KEY);
                    if (StringUtils.isNotBlank(fromUrl)) {
                        resp.sendRedirect(fromUrl);
                        return;
                    } else {
                        resp.getWriter().print("username=" + sessionManager.getLoginInfo(sid) + " you can continue shopping");
                        return;
                    }
                }
            }
        }

        // 无登录态, 验证账密
        String username = req.getParameter(UserCenterConstants.USERNAME_PARAM_KEY);
        String password = req.getParameter(UserCenterConstants.PASSWORD_PARAM_KEY);

        if (credentialManager.checkCredential(username, password)) {
            // 账密验证成功, 颁发登录态
            String sid = sessionManager.login(username);
            resp.addCookie(new Cookie(UserCenterConstants.SESSION_ID, sid));
        } else {
            // 账密验证失败, 显示错误
            resp.getWriter().write("Illegal credential");
        }
        return;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }
}
