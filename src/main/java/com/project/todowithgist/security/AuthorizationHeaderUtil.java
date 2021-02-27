package com.project.todowithgist.security;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationHeaderUtil {

	private final OAuth2AuthorizedClientService auth2AuthorizedClientService;

	public AuthorizationHeaderUtil(OAuth2AuthorizedClientService auth2AuthorizedClientService) {
		this.auth2AuthorizedClientService = auth2AuthorizedClientService;
	}

	public String getAuthorizationHeader() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
		OAuth2AuthorizedClient auth2AuthorizedClient = auth2AuthorizedClientService.loadAuthorizedClient(
				auth2AuthenticationToken.getAuthorizedClientRegistrationId(), auth2AuthenticationToken.getName());

		OAuth2AccessToken accessToken = auth2AuthorizedClient.getAccessToken();

		if (accessToken == null) {
			return null;
		} else {
			String tokenType = accessToken.getTokenType().getValue();
			String tokenValue = accessToken.getTokenValue();

			return String.format("%s %s", tokenType, tokenValue);
		}
	}
}
