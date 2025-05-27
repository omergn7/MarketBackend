package com.example.marketfiyat.Controller;

import com.example.marketfiyat.DTO.UrunDetayDto;
import com.example.marketfiyat.Model.*;
import com.example.marketfiyat.Repository.BarkodRepository;
import com.example.marketfiyat.Repository.MarketUrunRepository;
import com.example.marketfiyat.Repository.UrunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/urunler")
public class UrunController {

    @Autowired
    private MarketUrunRepository marketUrunRepository;

    @Autowired
    private BarkodRepository barkodRepository;

    @Autowired
    private UrunRepository urunRepository; // ✅ Arama için eklendi

    // ✅ 1. Ürün detayları
    @GetMapping("/detay/{barkodId}")
    public ResponseEntity<UrunDetayDto> getUrunDetay(@PathVariable int barkodId) {
        Optional<Barkod> barkodOpt = barkodRepository.findById(barkodId);

        if (barkodOpt.isPresent()) {
            Barkod barkod = barkodOpt.get();
            Urun urun = barkod.getUrun();
            BesinDegeri besin = urun != null ? urun.getBesinDegeri() : null;

            UrunDetayDto dto = new UrunDetayDto(
                    urun.getUrunName(),
                    urun.getUrunGorsel(),
                    urun.getUrunIcerik(),
                    urun.getUlke() != null ? urun.getUlke().getUlkeAdi() : null,
                    urun.getKategori() != null ? urun.getKategori().getKategoriAdi() : null,
                    besin != null && besin.getEnerjiKcal() != null ? besin.getEnerjiKcal().doubleValue() : null,
                    besin != null ? besin.getYag() : null,
                    besin != null ? besin.getDoymusYag() : null,
                    besin != null ? besin.getKarbonhidrat() : null,
                    besin != null ? besin.getSeker() : null,
                    besin != null ? besin.getProtein() : null,
                    besin != null ? besin.getTuz() : null
            );

            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ✅ 2. Barkodla karşılaştırma yapan endpoint
    @GetMapping("/karsilastir")
    public ResponseEntity<?> karsilastirBarkoddan(@RequestParam("barkod") String rawKod) {
        Pattern pattern = Pattern.compile("\\b\\d{8}\\b");
        Matcher matcher = pattern.matcher(rawKod);

        if (!matcher.find()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Geçerli 8 haneli barkod bulunamadı."));
        }

        String barkod = matcher.group();
        System.out.println("🎯 Gelen Barkod: " + barkod);

        Optional<Barkod> barkodOpt = barkodRepository.findFirstByBarkod(barkod);
        if (barkodOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Ürün bulunamadı", "barkod", barkod));
        }

        Barkod anaBarkod = barkodOpt.get();
        Urun urun = anaBarkod.getUrun();
        System.out.println("🎯 Barkodla eşleşen ürün ID: " + urun.getUrunId());

        List<MarketUrun> fiyatlar = marketUrunRepository.findLatestFiyatlarByUrunId(urun.getUrunId());

        List<Map<String, Object>> karsilastirma = new ArrayList<>();
        for (MarketUrun fiyat : fiyatlar) {
            Map<String, Object> item = new HashMap<>();
            item.put("market", fiyat.getMarket().getMarketName());
            item.put("fiyat", fiyat.getFiyat());
            item.put("barkodId", fiyat.getBarkod().getBarkodId());
            karsilastirma.add(item);
        }

        karsilastirma.sort(Comparator.comparingDouble(o -> (Double) o.get("fiyat")));

        String ulkeAdi = urun.getUlke() != null ? urun.getUlke().getUlkeAdi() : "Bilinmiyor";
        String bayrakEmoji = getBayrakEmoji(ulkeAdi);

        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("urunAdi", urun.getUrunName());
        dto.put("urunGorsel", urun.getUrunGorsel());
        dto.put("barkod", barkod);
        dto.put("ulke", ulkeAdi);
        dto.put("bayrak", bayrakEmoji);
        dto.put("karsilastirma", karsilastirma);

        return ResponseEntity.ok(dto);
    }

    public String getBayrakEmoji(String ulkeAdi) {
        System.out.println("🔎 GİRİLEN ülkeAdi: '" + ulkeAdi + "'");
        ulkeAdi = Normalizer.normalize(ulkeAdi, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT);
        System.out.println("🔎 TEMİZLENMİŞ ülkeAdi: '" + ulkeAdi + "'");

        if (ulkeAdi.contains("turkiye")) return "🇹🇷";
        if (ulkeAdi.contains("almanya")) return "🇩🇪";
        if (ulkeAdi.contains("amerika") || ulkeAdi.contains("abd")) return "🇺🇸";
        if (ulkeAdi.contains("fransa")) return "🇫🇷";
        if (ulkeAdi.contains("cin")) return "🇨🇳";
        if (ulkeAdi.contains("italya")) return "🇮🇹";
        return "🏳️";
    }

    // ✅ 3. Ürün arama endpoint'i
    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> searchUrun(@RequestParam String query) {
        List<Urun> result = urunRepository.findByUrunNameContainingIgnoreCase(query);

        List<Map<String, Object>> simpleList = result.stream().map(urun -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", urun.getUrunId());
            map.put("name", urun.getUrunName());
            map.put("urunGorsel", urun.getUrunGorsel()); // 🟢 Burası önemli!
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(simpleList);
    }



}
