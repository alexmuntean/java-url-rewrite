package org.urlrewrite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class URLRewrite
 */
public class URLRewrite implements Filter {
    public static final List<RuleProcessor> processors = new ArrayList<RuleProcessor>();

    /**
     * Default constructor.
     */
    public URLRewrite() {
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
	    FilterChain chain) throws IOException, ServletException {
	for (RuleProcessor processor : processors) {
	    if (processor.canDispatch(request)) {
		processor.dispatch(request, response);
		return;
	    }
	}

	// pass the request along the filter chain
	chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
	if (processors.size() > 0)
	    return;

	List<Rule> rules = Config.getRules(fConfig.getServletContext()
		.getResourceAsStream("/WEB-INF/url-rewriting.xml"));
	for (Rule rule : rules) {
	    processors.add(new RuleProcessor(rule));
	}
    }

}
