package org.urlrewrite;

class Rule {
    private From from;
    private To to;
    private Class<?> handlerClass = DefaultURLHandler.class;

    public From getFrom() {
	return from;
    }

    public void setFrom(From from) {
	this.from = from;
    }

    public To getTo() {
	return to;
    }

    public void setTo(To to) {
	this.to = to;
    }

    public void setHandler(String className) throws Exception {
	// allow default if none set
	if (className == null)
	    return;

	@SuppressWarnings("unchecked")
	Class<? extends URLRewrite> clazz = (Class<? extends URLRewrite>) Class
		.forName(className);
	handlerClass = clazz;
    }

    public URLHandler getHandler() {
	try {
	    return (URLHandler) handlerClass.newInstance();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	// should never happen
	return null;
    }
}
