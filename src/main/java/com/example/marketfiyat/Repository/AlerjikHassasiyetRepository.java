package com.example.marketfiyat.Repository;

import com.example.marketfiyat.Model.AlerjikHassasiyet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlerjikHassasiyetRepository extends JpaRepository<AlerjikHassasiyet, Integer> {
    Optional<AlerjikHassasiyet> findByAdiIgnoreCase(String adi);

}
