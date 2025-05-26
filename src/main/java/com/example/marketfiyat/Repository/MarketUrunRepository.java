
package com.example.marketfiyat.Repository;

import com.example.marketfiyat.Model.MarketUrun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarketUrunRepository extends JpaRepository<MarketUrun, Integer> {
    List<MarketUrun> findByMarket_MarketId(Integer marketId);
    List<MarketUrun> findAllByBarkod_Urun_UrunId(int urunId);
    List<MarketUrun> findByBarkod_BarkodId(int barkodId);
    @Query("""
SELECT mu FROM MarketUrun mu
WHERE mu.barkod.urun.urunId = :urunId
AND mu.fiyat IS NOT NULL
AND (
    mu.fiyatGuncellemeTarihi IS NULL OR
    mu.fiyatGuncellemeTarihi = (
        SELECT MAX(mu2.fiyatGuncellemeTarihi)
        FROM MarketUrun mu2
        WHERE mu2.barkod.urun.urunId = :urunId
        AND mu2.market.marketId = mu.market.marketId
    )
)
""")
    List<MarketUrun> findLatestFiyatlarByUrunId(@Param("urunId") int urunId);

}
