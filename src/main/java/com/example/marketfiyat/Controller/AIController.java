package com.example.marketfiyat.Controller;

import com.example.marketfiyat.Model.*;
import com.example.marketfiyat.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    private UrunRepository urunRepository;

    @PostMapping("/yorum")
    public ResponseEntity<?> yapayZekaYorum(
            @RequestParam Integer urunId,
            @RequestBody Map<String, List<Map<String, Object>>> bilgiler
    ) {
        Optional<Urun> urunOpt = urunRepository.findById(urunId);
        if (urunOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "Ürün bulunamadı", "urunId", urunId));
        }

        Urun urun = urunOpt.get();
        BesinDegeri besin = urun.getBesinDegeri();
        if (besin == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Ürünün besin değeri verisi yok"));
        }

        // ✅ Ürün gramajını ürün isminden çıkar
        Integer gramaj = extractGramaj(urun.getUrunName());
        if (gramaj == null || gramaj == 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "Ürün gramajı bulunamadı veya 0"));
        }

        // ✅ Normalize oran: tüm veriler 100g/ml üzerinden girildiği için
        double oran = gramaj / 100.0;


        List<String> hastaliklar = bilgiler.getOrDefault("hastaliklar", List.of()).stream()
                .map(obj -> Objects.toString(obj.get("adi"), "").toLowerCase(Locale.ROOT))
                .toList();

        List<String> alerjenler = bilgiler.getOrDefault("alerjenler", List.of()).stream()
                .map(obj -> Objects.toString(obj.get("adi"), "").toLowerCase(Locale.ROOT))
                .toList();

        List<String> yorumlar = new ArrayList<>();

        // ✅ Sporcuya uygun mu?
        Double protein = safeMultiply(besin.getProtein(), oran);
        Double yag = safeMultiply(besin.getYag(), oran);
        Double seker = safeMultiply(besin.getSeker(), oran);
        if (protein > 5 && yag < 15 && seker < 10) {
            yorumlar.add("✅ Sporcuya uygun");
        }

        // 🩺 Diyabet
        if (hastaliklar.contains("diyabet") && seker != null) {
            yorumlar.add(
                    (seker < 5 ? "✅ Diyabet hastası için uygun" : "❌ Diyabet hastası için uygun değil") +
                            "\n  Üründeki şeker: " + seker + " g\n  Olması gereken: < 5 g"
            );
        }

        // 🧂 Hipertansiyon
        Double tuz = safeMultiply(besin.getTuz(), oran);
        if (hastaliklar.contains("hipertansiyon") && tuz != null) {
            yorumlar.add(
                    (tuz < 0.5 ? "✅ Hipertansiyon hastası için uygun" : "❌ Hipertansiyon hastası için tuz yüksek") +
                            "\n  Üründeki tuz: " + tuz + " g\n  Olması gereken: < 0.5 g"
            );
        }

        // ❤️ Kalp hastalığı
        Double doymusYag = safeMultiply(besin.getDoymusYag(), oran);
        Double transYag = safeMultiply(besin.getTransYag(), oran);
        if (hastaliklar.contains("kalp") && doymusYag != null && transYag != null) {
            yorumlar.add(
                    (doymusYag < 3 && transYag < 1 ? "✅ Kalp hastası için uygun" : "❌ Kalp hastası için uygun değil") +
                            "\n  Doymuş yağ: " + doymusYag + " g, Trans yağ: " + transYag + " g\n  Olması gereken: Doymuş < 3 g, Trans < 1 g"
            );
        }

        // 🍔 Obezite
        Double enerji = safeMultiply(besin.getEnerjiKcal(), oran);
        if (hastaliklar.contains("obezite") && enerji != null) {
            yorumlar.add(
                    (enerji > 400 ? "❌ Obezite için uygun değil" : "✅ Obezite için uygun") +
                            "\n  Üründeki enerji: " + enerji + " kcal\n  Olması gereken: < 400 kcal"
            );
        }

        // 🌾 Çölyak
        String icerik = Optional.ofNullable(urun.getUrunIcerik()).orElse("").toLowerCase();
        if (hastaliklar.contains("çölyak")) {
            Set<String> urunAlerjenleri = new HashSet<>();
            if (urun.getAlerjenler() != null) {
                for (Alerjen a : urun.getAlerjenler()) {
                    urunAlerjenleri.add(a.getAlerjenAdi().toLowerCase(Locale.ROOT));
                }
            }

            boolean glutenAlerjenVar = urunAlerjenleri.contains("gluten");
            boolean icerikteGlutenli = icerik.contains("gluten") || icerik.contains("arpa") || icerik.contains("buğday") || icerik.contains("çavdar");

            if (glutenAlerjenVar || icerikteGlutenli) {
                yorumlar.add("⚠️ Çölyak hastası için gluten içeriyor");
            } else {
                yorumlar.add("✅ Çölyak hastası için gluten içermiyor");
            }
        }

        // 🍋 Reflü
        if (hastaliklar.contains("reflü")) {
            if (icerik.contains("asit") || icerik.contains("limon") || icerik.contains("sirke") || icerik.contains("sitrik") || icerik.contains("kafein")) {
                yorumlar.add("❌ Reflü hastası için uygun değil: Asitli içerik var\n  İçerik: " + urun.getUrunIcerik());
            } else {
                yorumlar.add("✅ Reflü hastası için uygun (asitli içerik bulunmadı)");
            }
        }

        // ⚠️ Alerjen kontrolü içerik bazlı
        for (String alerjen : alerjenler) {
            if (icerik.contains(alerjen.toLowerCase(Locale.ROOT))) {
                yorumlar.add("⚠️ Alerjen uyarısı: İçerikte '" + alerjen + "' geçiyor");
            }
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("urun", urun.getUrunName());
        response.put("yorumlar", yorumlar);

        return ResponseEntity.ok(response);
    }

    // 🧠 Gramaj çıkarma
    private Integer extractGramaj(String urunAdi) {
        Matcher matcher = Pattern.compile("(\\d+)\\s?(g|gr|ml)").matcher(urunAdi.toLowerCase());
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    // 🧠 Güvenli çarpım
    private Double safeMultiply(Number value, Double oran) {
        if (value == null || oran == null) return null;
        return value.doubleValue() * oran;
    }

}
