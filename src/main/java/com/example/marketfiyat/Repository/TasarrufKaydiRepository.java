package com.example.marketfiyat.Repository;

import com.example.marketfiyat.Model.TasarrufKaydi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TasarrufKaydiRepository extends JpaRepository<TasarrufKaydi, Integer> {
    List<TasarrufKaydi> findByKullaniciIdAndTarihAfter(Integer kullaniciId, LocalDateTime tarih);
}