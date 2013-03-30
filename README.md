What can you do:
=======

Use regular expressions to do url rewriting in java:

<h1>Setup</h1>
1. Copy the library into your project under `WEB-INF/lib/`
2. Add the `URLRewrite` filter into your `WEB-INF/web.xml`
<pre>
	&lt;filter&gt;
		&lt;filter-name&gt;URLRewrite&lt;/filter-name&gt;
		&lt;filter-class&gt;org.urlrewrite.URLRewrite&lt;/filter-class&gt;
	&lt;/filter&gt;
	&lt;filter-mapping&gt;
		&lt;filter-name&gt;URLRewrite&lt;/filter-name&gt;
		&lt;url-pattern&gt;/*&lt;/url-pattern&gt;
	&lt;/filter-mapping&gt;
</pre>
3. Create the file: `WEB-INF/url-rewriting.xml`
4. Add your rules

<h1>Example `url-rewriting.xml`</h1>
<pre>
&lt;?xml version="1.0" encoding="UTF-8" ?&gt;
&lt;url-rewriting&gt;
	&lt;rewrite from="/best/url$" to="/index.jsp?url=#{0}" /&gt;
	&lt;rewrite from="/best/url/permanently$" to="/index.jsp?url=#{0}" status="301" /&gt;
&lt;/url-rewriting&gt;
</pre>

Where `#{0}` will refer to your current URL

<h1>Advanced examples `url-rewriting.xml`</h1>
<pre>
&lt;?xml version="1.0" encoding="UTF-8" ?&gt;
&lt;url-rewriting&gt;
	&lt;rewrite from="/best/([^/]+)/$" to="/index.jsp?url=#{0}&mySelection=#{1}" /&gt;
	
	&lt;rewrite from="/index.jsp$" to="/new/url" status="301" /&gt;
	&lt;rewrite from="/new/url$" to="/index.jsp?nice-url=yes-it-worked" /&gt;
&lt;/url-rewriting&gt;
</pre>

<h1>More advanced examples `url-rewriting.xml`. Generate `to` in code</h1>
This is a strong feature because you can generate your own `new url` however you want: generate it using your database maybe.

You have to extend: `org.urlrewrite.URLHandler` and that's it.
Using `getResponse`, `getRequest` you have access to `HTTPServlet.*`. 

You can take a look at 
- <a href="https://github.com/alexmuntean/java-url-rewriting/blob/master/org.urlrewrite/src/main/java/org/urlrewrite/DefaultURLHandler.java">org.urlrewrite.DefaultURLHandler</a>


<pre>
&lt;?xml version="1.0" encoding="UTF-8" ? &gt;
&lt;url-rewriting&gt;
	&lt;rewrite from="/best/([^/]+)/$" handler="my.project.MyURLHandlerImplementation" /&gt;
	
	&lt;rewrite from="/index.jsp$" to="/new/url" status="301" /&gt;
	&lt;rewrite from="/new/url$" to="/index.jsp?nice-url=yes-it-worked" /&gt;
&lt;/url-rewriting&gt;
</pre>

<h1>Status codes</h1>
By default are considered only:

1. 301 - Permanent redirect
2. 302 - Found
3. 307 - Temporary redirect (since HTTP 1.1)

<b>But you can use:</b>

1. no status code and it will be default to `200`
2. any other code like `404`
