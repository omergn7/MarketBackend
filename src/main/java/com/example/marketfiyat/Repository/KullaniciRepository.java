package com.example.marketfiyat.Repository;

import com.example.marketfiyat.Model.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KullaniciRepository extends JpaRepository<Kullanici, Integer> {

    Optional<Kullanici> findByEmail(String email); // giriş için

    boolean existsByEmail(String email); // kayıtta email çakışmasını önler
}
