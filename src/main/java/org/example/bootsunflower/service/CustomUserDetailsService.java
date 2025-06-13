package org.example.bootsunflower.service;Add commentMore actions

import lombok.RequiredArgsConstructor;
import org.example.bootsunflower.entity.KakaoUser;
import org.example.bootsunflower.repository.KakaoUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final KakaoUserRepository kakaoUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        KakaoUser kakaoUser = kakaoUserRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Kakao user not found with username: " + username)
        );
        return User.builder()
                .username(kakaoUser.getUsername())
                .password("")
                .build();
    }
}