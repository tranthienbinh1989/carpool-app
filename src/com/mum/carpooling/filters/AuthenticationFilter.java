package com.mum.carpooling.filters;

import java.io.IOException;

import javax.print.DocFlavor.URL;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mum.carpooling.model.User;

/**
 * @author binhtran
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {
	private ServletContext context;
	
    /**
     * Default constructor. 
     */
    public AuthenticationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String uri = req.getRequestURI();
		this.context.log("Requested Resource::"+uri);
		System.out.println("Requested Resource::"+uri);
		HttpSession session = req.getSession(true);
		User currentUser = (User) session.getAttribute("currentUser");
		
		if(excludeFromFilter(uri)) {
			chain.doFilter(request, response);
		} else {
			if(!uri.endsWith("login") && currentUser == null){
				this.context.log("Unauthorized access request");
				res.sendRedirect("login");
			}else{
				// pass the request along the filter chain
				chain.doFilter(request, response);
			}
		}
	}
	
	private boolean excludeFromFilter(String path) {
	      if (path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png")) {
	    	  return true; // add more page to exclude here
	      } else {
	    	  return false;
	      }
   }

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
		this.context.log("AuthenticationFilter initialized");
	}

}
