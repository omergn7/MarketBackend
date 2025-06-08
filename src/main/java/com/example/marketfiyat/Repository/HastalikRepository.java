package com.example.marketfiyat.Repository;

import com.example.marketfiyat.Model.Hastalik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HastalikRepository extends JpaRepository<Hastalik, Integer> {
    Optional<Hastalik> findByAdiIgnoreCase(String adi);
}
