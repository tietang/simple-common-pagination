package commons.utils.jsp;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CacheFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Pragma", "No-cache");// HTTP 1.1
		response.setHeader("Cache-Control", "no-cache");// HTTP 1.0
		response.setHeader("Expires", "0");// 防止被proxy
		chain.doFilter(req, res);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}
}
