package com.mum.carpooling.view;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.mum.carpooling.model.User;

public class PostTag extends SimpleTagSupport {
	 public void doTag() throws JspException, IOException {
		 JspWriter out = getJspContext().getOut();

	        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
	        getJspContext().getAttribute("CurrentUser",PageContext.SESSION_SCOPE);
			HttpSession session = request.getSession(false);
			User CurrentUser = getUser();//getJspContext().getAttribute("CurrentUser",PageContext.SESSION_SCOPE);
		 
	 }
}