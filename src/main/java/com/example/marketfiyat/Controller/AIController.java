package com.example.marketfiyat.Controller;

import com.example.marketfiyat.Model.*;
import com.example.marketfiyat.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    private UrunRepository urunRepository;

    @PostMapping("/yorum")
    public ResponseEntity<?> yapayZekaYorum(
            @RequestParam Integer urunId,
            @RequestBody Map<String, List<String>> bilgiler
    ) {
        System.out.println("📥 İstek alındı: /api/ai/yorum");
        System.out.println("🔎 Gelen ürün ID: " + urunId);
        System.out.println("📦 Gelen body: " + bilgiler);

        Optional<Urun> urunOpt = urunRepository.findById(urunId);
        if (urunOpt.isEmpty()) {
            System.out.println("❌ Ürün bulunamadı.");
            return ResponseEntity.status(404).body(Map.of("error", "Ürün bulunamadı", "urunId", urunId));
        }

        Urun urun = urunOpt.get();
        BesinDegeri besin = urun.getBesinDegeri();
        if (besin == null) {
            System.out.println("⚠️ Besin değeri yok.");
            return ResponseEntity.badRequest().body(Map.of("error", "Ürünün besin değeri verisi yok"));
        }

        List<String> hastaliklar = bilgiler.getOrDefault("hastaliklar", List.of());
        List<String> alerjenler = bilgiler.getOrDefault("alerjenler", List.of());

        System.out.println("🩺 Hastalıklar: " + hastaliklar);
        System.out.println("🌰 Alerjenler: " + alerjenler);

        List<String> yorumlar = new ArrayList<>();

        // ✅ Sporcuya uygun mu?
        if (besin.getProtein() > 5 && besin.getYag() < 15 && besin.getSeker() < 10) {
            yorumlar.add("✅ Sporcuya uygun");
        }

        // 🩺 Diyabet
        if (hastaliklar.contains("diyabet")) {
            if (besin.getSeker() < 5)
                yorumlar.add("✅ Diyabet hastası için uygun");
            else
                yorumlar.add("❌ Diyabet hastası için uygun değil (şeker yüksek)");
        }

        // 🧂 Hipertansiyon
        if (hastaliklar.contains("hipertansiyon")) {
            if (besin.getTuz() < 0.5)
                yorumlar.add("✅ Hipertansiyon hastası için uygun");
            else
                yorumlar.add("❌ Hipertansiyon hastası için tuz yüksek");
        }

        // ❤️ Kalp hastalığı
        if (hastaliklar.contains("kalp")) {
            if (besin.getDoymusYag() < 3 && besin.getTransYag() < 1)
                yorumlar.add("✅ Kalp hastası için uygun");
            else
                yorumlar.add("❌ Kalp hastası için uygun değil (yağ oranı yüksek)");
        }

        // ⚠️ Alerjen kontrolü
        Set<String> urunAlerjenleri = new HashSet<>();
        if (urun.getAlerjenler() != null) {
            for (Alerjen a : urun.getAlerjenler()) {
                urunAlerjenleri.add(a.getAlerjenAdi().toLowerCase(Locale.ROOT));
            }
        }

        for (String kullaniciAlerjen : alerjenler) {
            if (urunAlerjenleri.contains(kullaniciAlerjen.toLowerCase(Locale.ROOT))) {
                yorumlar.add("⚠️ Alerjen uyarısı: '" + kullaniciAlerjen + "' içeriyor");
            }
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("urun", urun.getUrunName());
        response.put("yorumlar", yorumlar);

        System.out.println("✅ Yanıt oluşturuldu.");
        return ResponseEntity.ok(response);
    }
}
