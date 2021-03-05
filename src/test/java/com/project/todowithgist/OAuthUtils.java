package com.project.todowithgist;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

public class OAuthUtils {

	public static OAuth2User createUser(String name, String email) {
		Map<String, Object> authorityAttr = new HashMap<>();
		authorityAttr.put("key", "value");

		GrantedAuthority authority = new OAuth2UserAuthority(authorityAttr);

		Map<String, Object> attributes = new HashMap<>();
		attributes.put("sub", "1234567890");
		attributes.put("name", name);
		attributes.put("email", email);

		return new DefaultOAuth2User(Arrays.asList(authority), attributes, "sub");
	}

	public static Authentication getOauthAuthenticationFor(OAuth2User principal) {

		Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

		String authorizedClientRegistrationId = "my-oauth-client";

		return new OAuth2AuthenticationToken(principal, authorities, authorizedClientRegistrationId);
	}
}
