package com.example.marketfiyat.Repository;

import com.example.marketfiyat.Model.Urun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UrunRepository extends JpaRepository<Urun, Integer> {
    List<Urun> findByUrunNameContainingIgnoreCase(String urunName);
    // Besin değerleriyle birlikte bir ürünü eager fetch ile çek
    @Query("SELECT u FROM Urun u LEFT JOIN FETCH u.besinDegeri WHERE u.urunId = :urunId")
    Optional<Urun> findByUrunIdWithBesinDegeri(@Param("urunId") Integer urunId);

}
