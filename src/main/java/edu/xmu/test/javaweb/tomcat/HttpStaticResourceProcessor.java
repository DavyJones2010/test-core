package edu.xmu.test.javaweb.tomcat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;

public class HttpStaticResourceProcessor {
	static final Logger logger = Logger.getLogger(HttpStaticResourceProcessor.class);

	public void service(ServletRequest req, ServletResponse resp) {
		try {
			List<String> extractStaticResource = extractStaticResource(req.getServletContext());
			logger.info(extractStaticResource);

			PrintWriter writer = resp.getWriter();
			for (String oline : extractStaticResource) {
				writer.println(oline);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	List<String> extractStaticResource(ServletContext context) throws IOException, FileNotFoundException {
		String contextPath = context.getContextPath();
		String webRoot = context.getRealPath("/");
		List<String> resource = Lists.newArrayList();

		List<String> headers = Lists.newArrayList();
		List<String> contents = Lists.newArrayList();
		File file = new File(webRoot.concat(contextPath));
		if (file.exists()) {
			headers = Lists.newArrayList("HTTP/1.1 200 OK");
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line = null;
				while (null != (line = reader.readLine())) {
					contents.add(line);
				}
			}
		} else {
			headers = Lists.newArrayList("HTTP/1.1 404 File Not Found");
		}
		resource.addAll(headers);
		resource.addAll(contents);

		return resource;
	}
}
