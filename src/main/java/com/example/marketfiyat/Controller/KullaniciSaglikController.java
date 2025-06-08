package com.example.marketfiyat.Controller;

import com.example.marketfiyat.Model.*;
import com.example.marketfiyat.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/saglik")
public class KullaniciSaglikController {

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Autowired
    private KullaniciSaglikRepository kullaniciSaglikRepository;

    @Autowired
    private HastalikRepository hastalikRepository;

    @Autowired
    private AlerjikHassasiyetRepository alerjikHassasiyetRepository;

    // ✅ 1. Sağlık verilerini kaydet
    @PostMapping("/kaydet")
    public ResponseEntity<?> kaydet(@RequestParam Integer kullaniciId,
                                    @RequestBody Map<String, List<Integer>> body) {
        Optional<Kullanici> kullaniciOpt = kullaniciRepository.findById(kullaniciId);
        if (kullaniciOpt.isEmpty())
            return ResponseEntity.status(404).body(Map.of("error", "Kullanıcı bulunamadı"));

        Kullanici kullanici = kullaniciOpt.get();

        // Önceki sağlık verilerini sil
        List<KullaniciSaglik> oncekiler = kullaniciSaglikRepository.findByKullanici(kullanici);
        kullaniciSaglikRepository.deleteAll(oncekiler);

        List<KullaniciSaglik> yeniListe = new ArrayList<>();

        // ✅ Hastalıklar
        List<Integer> hastalikIds = body.getOrDefault("hastaliklar", List.of());
        for (Integer hId : hastalikIds) {
            hastalikRepository.findById(hId).ifPresent(h -> {
                yeniListe.add(new KullaniciSaglik(null, kullanici, "hastalik", h.getAdi().toLowerCase(Locale.ROOT)));
            });
        }

        // ✅ Alerjenler
        List<Integer> alerjenIds = body.getOrDefault("alerjenler", List.of());
        for (Integer aId : alerjenIds) {
            alerjikHassasiyetRepository.findById(aId).ifPresent(a -> {
                yeniListe.add(new KullaniciSaglik(null, kullanici, "alerjen", a.getAdi().toLowerCase(Locale.ROOT)));
            });
        }

        kullaniciSaglikRepository.saveAll(yeniListe);
        return ResponseEntity.ok(Map.of("mesaj", "Veriler başarıyla kaydedildi"));
    }

    // ✅ 2. Sağlık verilerini getir (id'li döner)
    @GetMapping("/getir")
    public ResponseEntity<?> getir(@RequestParam Integer kullaniciId) {
        Optional<Kullanici> kullaniciOpt = kullaniciRepository.findById(kullaniciId);
        if (kullaniciOpt.isEmpty())
            return ResponseEntity.status(404).body(Map.of("error", "Kullanıcı bulunamadı"));

        Kullanici kullanici = kullaniciOpt.get();
        List<KullaniciSaglik> liste = kullaniciSaglikRepository.findByKullanici(kullanici);

        List<Map<String, Object>> hastaliklar = new ArrayList<>();
        List<Map<String, Object>> alerjenler = new ArrayList<>();

        for (KullaniciSaglik s : liste) {
            Map<String, Object> map = new HashMap<>();
            map.put("adi", s.getAdi());
            Optional<Integer> id = Optional.empty();

            if ("hastalik".equals(s.getTip())) {
                id = hastalikRepository.findByAdiIgnoreCase(s.getAdi()).map(Hastalik::getId);
                id.ifPresent(val -> {
                    map.put("id", val);
                    hastaliklar.add(map);
                });
            } else if ("alerjen".equals(s.getTip())) {
                id = alerjikHassasiyetRepository.findByAdiIgnoreCase(s.getAdi()).map(AlerjikHassasiyet::getId);
                id.ifPresent(val -> {
                    map.put("id", val);
                    alerjenler.add(map);
                });
            }
        }

        return ResponseEntity.ok(Map.of(
                "hastaliklar", hastaliklar,
                "alerjenler", alerjenler
        ));
    }

    // ✅ 3. Tüm hastalıkları getir
    @GetMapping("/hastaliklar")
    public ResponseEntity<List<Map<String, Object>>> hastaliklar() {
        List<Map<String, Object>> sonuc = hastalikRepository.findAll().stream()
                .map(h -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", h.getId());
                    map.put("adi", h.getAdi());
                    return map;
                })
                .toList();

        return ResponseEntity.ok(sonuc);
    }

    // ✅ 4. Tüm alerjenleri getir
    @GetMapping("/alerjenler")
    public ResponseEntity<List<Map<String, Object>>> alerjenler() {
        List<Map<String, Object>> sonuc = alerjikHassasiyetRepository.findAll().stream()
                .map(a -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", a.getId());
                    map.put("adi", a.getAdi());
                    return map;
                })
                .toList();

        return ResponseEntity.ok(sonuc);
    }
}
