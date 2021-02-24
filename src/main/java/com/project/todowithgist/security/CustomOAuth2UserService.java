package com.project.todowithgist.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
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

//        try {
//        	return processOAuth2User(auth2UserRequest, oAuth2User);
//		} catch (Exception ex) {
//            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
//
//		}

		userRepository.save(new User(oAuth2User.getName(),oAuth2User.getAttribute("login")));
		return oAuth2User;

	}

	private OAuth2User processOAuth2User(OAuth2UserRequest auth2UserRequest, OAuth2User oAuth2User) {

//		return UserPrincipal.create(user, oAuth2User.getAttributes());

		return null;
	}
}
