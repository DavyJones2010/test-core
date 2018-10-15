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
 * 业务站点接收统一登录站点同步cookie请求的地址, 将cookie种到业务域下
 * http://pass.mydomain.com:port
 */
public class MyDomainPassServlet extends HttpServlet {
    SessionManager sessionManager = SessionManager.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    private void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sid = req.getParameter(UserCenterConstants.SESSION_ID);
        String fromUrl = req.getParameter(UserCenterConstants.FROM_URL_PARAM_KEY);
        if(StringUtils.isNotBlank(sid) && sessionManager.isLogin(sid)){
            // 在业务域种下cookie并跳转到业务地址
            Cookie cookie = new Cookie(UserCenterConstants.SESSION_ID, sid);
            resp.addCookie(cookie);
            resp.sendRedirect(fromUrl);
            return;
        }else{
            // 跳转到统一登录中心
            String loginFullUrl = buildLoginUrl(req);
            System.out.println("loginFullUrl=" + loginFullUrl);
            resp.sendRedirect(loginFullUrl);
            return;
        }
    }

    private String buildLoginUrl(HttpServletRequest req) {
        String fromUrl = req.getParameter(UserCenterConstants.FROM_URL_PARAM_KEY);
        String loginUrl = req.getParameter(UserCenterConstants.LOGIN_URL_PARAM_KEY);
        String loginFullUrl = loginUrl + "?" + UserCenterConstants.FROM_URL_PARAM_KEY + "=" + fromUrl;
        return loginFullUrl;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }
}
