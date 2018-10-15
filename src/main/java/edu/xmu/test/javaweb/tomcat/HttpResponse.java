package edu.xmu.test.javaweb.tomcat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

public class HttpResponse extends HttpServletResponseAdapter {
	Locale loc;
	String webRoot;
	PrintWriter writer;

	public HttpResponse(OutputStream os) {
		super();
		this.writer = new PrintWriter(os, true);
	}

	public HttpResponse(String webRoot) {
		super();
		this.webRoot = webRoot;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return writer;
	}
}
