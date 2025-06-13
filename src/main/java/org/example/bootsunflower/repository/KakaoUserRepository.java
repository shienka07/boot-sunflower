package org.example.bootsunflower.repository;

import org.example.bootsunflower.entity.KakaoUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoUserRepository extends JpaRepository<KakaoUser, String> {
    KakaoUser findByUsername(String username);
}
