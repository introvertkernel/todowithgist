package com.project.todowithgist.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomFilterTest {
	@InjectMocks
	CustomFilter filter;

	@MockBean
	HttpServletRequest request;

	@MockBean
	HttpServletResponse response;

	@MockBean
	HttpSession session;

	@BeforeEach
	public void setUp() throws Exception {

		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
	}

	@Test
	public void testDoFilter() throws IOException, ServletException {

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/");
		request.setSession(session);

		FilterChain filterChain = mock(FilterChain.class);
		SecurityContextImpl sci = mock(SecurityContextImpl.class);

//		when(request.getRequestURI()).thenReturn("/");
//		when(request.getSession(true)).thenReturn(session);
		when(session.getAttribute("SPRING_SECURITY_CONTEXT")).thenReturn(sci);
		Authentication auth = mock(Authentication.class);
		when(sci.getAuthentication()).thenReturn(auth);

		OAuth2User user = mock(OAuth2User.class);
		when(auth.getPrincipal()).thenReturn(user);

		filter.doFilter(request, response, filterChain);

//		assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_OK);
//		verify(filterChain).doFilter(request, response);
//		verify(response).sendRedirect("/home.html");

	}
}
