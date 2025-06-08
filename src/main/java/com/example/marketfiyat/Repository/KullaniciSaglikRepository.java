package com.example.marketfiyat.Repository;

import com.example.marketfiyat.Model.KullaniciSaglik;
import com.example.marketfiyat.Model.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KullaniciSaglikRepository extends JpaRepository<KullaniciSaglik, Integer> {
    List<KullaniciSaglik> findByKullanici(Kullanici kullanici);
}
