package edu.xmu.test.spring.aop.flowcontrol;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台controller,用来专门实现配置文件的更改<br>
 * curl 'http://localhost:8080/configUpdate?key=value&key2=value2'
 * 
 * @author davyjones
 *
 */
public class ConfigUpdateController extends HttpServlet {
	List<ConfigUpdateListener> listeners;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Enumeration<String> attributeNames = req.getAttributeNames();
		Properties p = new Properties();
		while (attributeNames.hasMoreElements()) {
			String nextElement = attributeNames.nextElement();
			Object attribute = req.getAttribute(nextElement);
			p.put(nextElement, attribute);
		}

		for (ConfigUpdateListener configUpdateListener : listeners) {
			configUpdateListener.updateConfig(p);
		}
	}

}
