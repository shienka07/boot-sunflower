package org.example.bootsunflower.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.bootsunflower.auth.JwtTokenProvider;
import org.example.bootsunflower.entity.KakaoUser;
import org.example.bootsunflower.repository.KakaoUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
    private final KakaoUserRepository kakaoUserRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String kakaoId = (String) profile.get("kakao_id");
        String nickname = (String) profile.get("nickname");
        String picture = (String) profile.get("picture");

        String username = "kakao_%s".formatted(kakaoId);

        // DB 저장 혹은 조회
        try {
            kakaoUserRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("No Kakao"));
        } catch (UsernameNotFoundException e) {
            // 없다면 가입
            KakaoUser newKakaoUser = new KakaoUser();
            newKakaoUser.setUsername(username);
            newKakaoUser.setName(nickname);
            newKakaoUser.setProfileImage(picture);
            kakaoUserRepository.save(newKakaoUser);
        }
        return oAuth2User;
    }

    @Service
    @RequiredArgsConstructor
    public static class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
        private final JwtTokenProvider jwtTokenProvider;

        @Value("${front-end.redirect}")
        private String frontEndRedirect;

        @Override
        public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String id = oAuth2User.getAttributes().get("id").toString();
            String username = "kakao_%s".formatted(id);
            String token = jwtTokenProvider.generateToken(new UsernamePasswordAuthenticationToken(username, ""));
            String redirectUri = UriComponentsBuilder
                    .fromUriString(frontEndRedirect)
                    .queryParam("token", token)
                    .build().toUriString();
            resp.sendRedirect(redirectUri);
        }
    }
}