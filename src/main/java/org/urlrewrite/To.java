package org.urlrewrite;

class To {
    private String url;
    private int httpStatus = 200;

    public To(String url) {
	this.url = url;
    }

    public String getUrl(String[] params) {
	String newUrl = url;
	for (int i = 0; i < params.length; i++) {
	    String param = params[i];
	    newUrl = newUrl.replace("#{" + i + "}", param);
	}
	return newUrl;
    }

    public void setHttpStatus(int status) {
	this.httpStatus = status;
    }

    public int getHttpStatus() {
	return httpStatus;
    }

    public String toString() {
	return url;
    }
}
