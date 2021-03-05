package com.project.todowithgist.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.project.todowithgist.domain.User;
import com.project.todowithgist.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest auth2UserRequest) {
		OAuth2User oAuth2User = super.loadUser(auth2UserRequest);

		userRepository
				.save(new User(oAuth2User.getName(), oAuth2User.getAttribute("login"), getToken(auth2UserRequest)));
		return oAuth2User;

	}

	private String getToken(OAuth2UserRequest request) {
		OAuth2AccessToken accessToken = request.getAccessToken();

		if (accessToken == null) {
			return null;
		} else {
			String tokenType = accessToken.getTokenType().getValue();
			String tokenValue = accessToken.getTokenValue();

			return String.format("%s %s", tokenType, tokenValue);
		}
	}

}
