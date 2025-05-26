package com.example.marketfiyat.Controller;

import com.example.marketfiyat.DTO.MarketUrunDto;
import com.example.marketfiyat.Model.MarketUrun;
import com.example.marketfiyat.Repository.MarketUrunRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/market-urun")
@CrossOrigin(origins = "*")
public class MarketUrunController {

    private final MarketUrunRepository marketUrunRepository;

    public MarketUrunController(MarketUrunRepository marketUrunRepository) {
        this.marketUrunRepository = marketUrunRepository;
    }

    // 🔴 TÜM verileri döner → backend-debug, detaylı admin panel gibi kullanımlar için
    @GetMapping("/market/{marketId}")
    public List<MarketUrun> getUrunlerByMarketId(@PathVariable Integer marketId) {
        return marketUrunRepository.findByMarket_MarketId(marketId);
    }

    // 🟢 Sadece gerekli alanları (özeti) döner → mobilde ürün listesi gibi sade görünüm için
    @GetMapping("/ozet/{marketId}")
    public List<MarketUrunDto> getUrunOzetleriByMarketId(@PathVariable Integer marketId) {
        List<MarketUrun> urunler = marketUrunRepository.findByMarket_MarketId(marketId);

        return urunler.stream()
                .map(mu -> new MarketUrunDto(
                        mu.getBarkod().getBarkodId(),
                        mu.getBarkod().getUrun().getUrunName(),
                        mu.getBarkod().getUrun().getUrunGorsel(),
                        mu.getFiyat(),
                        mu.getBarkod().getUrun().getUlke() != null
                                ? mu.getBarkod().getUrun().getUlke().getUlkeAdi()
                                : null
                ))
                .toList();
    }

}
