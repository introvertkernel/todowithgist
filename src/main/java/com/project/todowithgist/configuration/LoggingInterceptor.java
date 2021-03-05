package com.project.todowithgist.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.nimbusds.jose.util.StandardCharset;

//@Configuration
//public class LoggingInterceptor implements ClientHttpRequestInterceptor {

//	static Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);
//
//	@Override
//	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
//			throws IOException {
//		LOGGER.debug("Request Body: {} ", new String(body, StandardCharset.UTF_8));
//		System.out.println("Request Body: {} " + new String(body, StandardCharset.UTF_8));
//		ClientHttpResponse response = execution.execute(request, body);
//		InputStreamReader isr = new InputStreamReader(response.getBody(), StandardCharset.UTF_8);
//		String bodyStr = new BufferedReader(isr).lines().collect(Collectors.joining("\n"));
//		LOGGER.debug("Response Body: {} ", bodyStr);
//		System.out.println("Response Body: {} " + bodyStr);
//		return response;
//	}

//}
