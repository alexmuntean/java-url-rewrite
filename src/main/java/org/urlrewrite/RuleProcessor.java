package org.urlrewrite;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

class RuleProcessor {
    protected Rule rule;

    public RuleProcessor(Rule rule) {
	this.rule = rule;
    }

    public boolean canDispatch(ServletRequest request) {
	URLHandler handler = rule.getHandler();
	handler.initialize(request, null);
	handler.setRule(rule);
	return handler.canDispatch();
    }

    public void dispatch(ServletRequest request, ServletResponse response)
	    throws ServletException, IOException {
	URLHandler handler = rule.getHandler();
	handler.initialize(request, response);
	handler.setRule(rule);
	handler.dispatch();
    }
}
