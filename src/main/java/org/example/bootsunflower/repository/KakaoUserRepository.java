package org.example.bootsunflower.repository;

import org.example.bootsunflower.entity.KakaoUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KakaoUserRepository extends JpaRepository<KakaoUser, String> {
    Optional<KakaoUser> findByUsername(String username);
}
