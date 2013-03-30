package org.urlrewrite;

import java.io.IOException;
import java.util.regex.Matcher;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class URLHandler {
    private Rule rule;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public void initialize(ServletRequest request, ServletResponse response) {
	this.request = (HttpServletRequest) request;
	this.response = (HttpServletResponse) response;
    }

    public void setRule(Rule rule) {
	this.rule = rule;
    }

    public Rule getRule() {
	return this.rule;
    }

    protected HttpServletRequest getRequest() {
	return request;
    }

    protected HttpServletResponse getResponse() {
	return response;
    }

    protected String[] getParams() {
	Matcher m = rule.getFrom().getPattern().matcher(getUrl());
	m.find();
	String[] params = new String[m.groupCount() + 1];
	params[0] = getUrl();
	for (int i = 0; i < m.groupCount(); i++) {
	    params[i] = m.group(i + 1);
	}

	return params;
    }

    public final boolean canDispatch() {
	String url = getUrl();
	From from = rule.getFrom();
	return from.matches(url);
    }

    public abstract void dispatch() throws ServletException, IOException;

    protected String getUrl() {
	return request.getServletPath();
    }
}
