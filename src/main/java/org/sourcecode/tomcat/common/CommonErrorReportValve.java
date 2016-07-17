package org.sourcecode.tomcat.common;

import java.io.IOException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ErrorReportValve;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class CommonErrorReportValve extends ErrorReportValve {

	protected Log log = LogFactory.getLog(getClass());

	@Override
	protected void report(Request request, Response response, Throwable throwable) {
		String stackTrace = getPartialServletStackTrace(throwable);
		int statusCode = response.getStatus();
		try {
			log.error("statusCode = " + statusCode + ",exception = " + stackTrace);
			response.sendError(statusCode, "System is Occur a Error ...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
