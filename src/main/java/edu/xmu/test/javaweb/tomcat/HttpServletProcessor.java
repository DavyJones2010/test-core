package edu.xmu.test.javaweb.tomcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

public class HttpServletProcessor {
	static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

	HttpServletResourceProcessor httpServletResourceProcessor = new HttpServletResourceProcessor();
	HttpStaticResourceProcessor httpStaticResourceProcessor = new HttpStaticResourceProcessor();
	HttpConnector httpConnector;

	public HttpServletProcessor(HttpConnector httpConnector) {
		this.httpConnector = httpConnector;
	}

	public void service(HttpServletRequest req, HttpServletResponse resp) {
		String requestUri = req.getRequestURI();
		if (SHUTDOWN_COMMAND.equals(requestUri)) {
			httpConnector.stopped = true;
		} else if (requestUri.startsWith("/servlet/")) {
			httpServletResourceProcessor.service(req, resp);
		} else {
			httpStaticResourceProcessor.service(req, resp);
		}
	}

	public void process(Socket socket) {
		try {
			SocketInputStream input = new SocketInputStream(socket.getInputStream());
			RequestLine reqLine = input.readRequestLine();
			Map<String, String> headers = input.parseRequestHeaders();
			HttpServletRequest req = new HttpRequest(reqLine, headers);
			HttpServletResponse resp = new HttpResponse(socket.getOutputStream());
			service(req, resp);
			socket.shutdownInput();
			socket.shutdownOutput();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class SocketInputStream {
		InputStream is;
		BufferedReader reader;

		public SocketInputStream(InputStream is) {
			super();
			this.is = is;
			this.reader = new BufferedReader(new InputStreamReader(is));
		}

		RequestLine readRequestLine() {
			RequestLine reqLine = new RequestLine();
			try {
				String line = reader.readLine();
				List<String> requests = Splitter.on(' ').trimResults().splitToList(line);
				Preconditions.checkArgument(3 == requests.size());
				reqLine.requestMethod = requests.get(0);
				reqLine.requestProtocol = requests.get(2);

				String[] strs = StringUtils.split(requests.get(1), '?');
				Preconditions.checkArgument(strs.length > 0);
				if (1 == strs.length) {
					reqLine.requestUri = strs[0];
					reqLine.requestParams = Maps.newHashMap();
				} else {
					reqLine.requestUri = strs[0];
					reqLine.requestParams = Splitter.on('&').withKeyValueSeparator('=').split(strs[1]);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return reqLine;
		}

		Map<String, String> parseRequestHeaders() {
			Map<String, String> headers = Maps.newHashMap();
			String line = null;
			try {
				while (null != (line = reader.readLine()) && !StringUtils.isEmpty(line)) {
					List<String> strs = Splitter.on(": ").splitToList(line);
					Preconditions.checkArgument(2 == strs.size());
					headers.put(strs.get(0), strs.get(1));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return headers;
		}
	}

	static class RequestLine {
		String requestMethod;
		String requestUri;
		Map<String, String> requestParams;
		String requestProtocol;
	}
}
