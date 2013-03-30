package org.urlrewrite;

import java.util.regex.Pattern;

class From {
    private String regex;
    private Pattern pattern;

    public From(String regex) {
	this.regex = regex;
	this.pattern = Pattern.compile(regex);
    }

    public boolean matches(String url) {
	return Pattern.matches(regex, url);
    }

    public Pattern getPattern() {
	return pattern;
    }
}
