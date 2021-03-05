package com.project.todowithgist.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
public class CustomFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

//		if (!isAuthenticated() && "/home.html".equals(httpServletRequest.getRequestURI())) {
//			String encodedRedirectURL = ((HttpServletResponse) response)
//					.encodeRedirectURL(httpServletRequest.getContextPath() + "/");
//
//			httpServletResponse.setStatus(307);
//			httpServletResponse.setHeader("Location", encodedRedirectURL);
//		}

		if (isAuthenticated() && "/".equals(httpServletRequest.getRequestURI())) {
			String encodedRedirectURL = ((HttpServletResponse) response)
					.encodeRedirectURL(httpServletRequest.getContextPath() + "/home.html");

			httpServletResponse.setStatus(307);
			httpServletResponse.setHeader("Location", encodedRedirectURL);

		}
		chain.doFilter(httpServletRequest, httpServletResponse);

	}

	private boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
			return false;
		}
		return authentication.isAuthenticated();
	}

}
