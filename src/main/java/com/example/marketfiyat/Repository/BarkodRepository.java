package com.example.marketfiyat.Repository;



import com.example.marketfiyat.Model.Barkod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BarkodRepository extends JpaRepository<Barkod, Integer> {

    // 🔁 Belirli bir ürün ID'sine ait tüm barkodları getir (market bazlı)
    List<Barkod> findAllByUrun_UrunId(int urunId);

    // 🔍 Verilen barkoda ait tüm eşleşen kayıtları getir (şüpheli, çoklu olabilir)
    List<Barkod> findAllByBarkod(String barkod);

    // ✅ İlk eşleşen barkodu getir (tekil kullanım için ideal)
    Optional<Barkod> findFirstByBarkod(String barkod);
}
