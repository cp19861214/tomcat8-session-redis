package org.sourcecode.tomcat.common;

import java.io.IOException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ErrorReportValve;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class CommonErrorReportValve extends ErrorReportValve {

	protected Log log = LogFactory.getLog(getClass());

	private String errorPage = "/error.html";

	@Override
	protected void report(Request request, Response response, Throwable throwable) {
		String stackTrace = getPartialServletStackTrace(throwable);
		int statusCode = response.getStatus();
		try {
			log.error("statusCode = " + statusCode + ",exception:\r\n" + stackTrace);
			response.sendRedirect(errorPage + "?msg=System is Occur a Error", statusCode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
