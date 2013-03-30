package org.urlrewrite;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

class DefaultURLHandler extends URLHandler {
    @Override
    public void dispatch() throws ServletException, IOException {
	String newUrl = generateToUrl();

	RequestDispatcher dispatcher = getRequest()
		.getRequestDispatcher(newUrl);

	int status = getRule().getTo().getHttpStatus();
	switch (status) {
	case 301:
	case 302:
	case 307:
	    getResponse().setStatus(status);
	    getResponse().setHeader("Location",
		    getRequest().getContextPath() + newUrl);
	    getResponse().setHeader("Connection", "close");
	    break;
	case 200:
	    dispatcher.forward(getRequest(), getResponse());
	    break;
	default:
	    getResponse().setStatus(status);
	    dispatcher.forward(getRequest(), getResponse());
	    break;
	}
    }

    private String generateToUrl() {
	String[] fromParams = getParams();
	Rule rule = getRule();
	To to = rule.getTo();
	return to.getUrl(fromParams);
    }

}
