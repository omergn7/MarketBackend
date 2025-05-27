// ✅ Temizlenmiş KullaniciController.java (aylikTasarruf kaldırıldı)
package com.example.marketfiyat.Controller;

import com.example.marketfiyat.Model.Kullanici;
import com.example.marketfiyat.Model.TasarrufKaydi;
import com.example.marketfiyat.Repository.KullaniciRepository;
import com.example.marketfiyat.Repository.TasarrufKaydiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/kullanici")
public class KullaniciController {

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Autowired
    private TasarrufKaydiRepository tasarrufKaydiRepository;

    @PostMapping("/giris")
    public ResponseEntity<?> kullaniciGiris(@RequestBody Map<String, String> login) {
        String email = login.get("email");
        String parola = login.get("parola");

        Optional<Kullanici> kullaniciOpt = kullaniciRepository.findByEmail(email);
        if (kullaniciOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Kullanıcı bulunamadı.");
        }

        Kullanici kullanici = kullaniciOpt.get();
        if (!kullanici.getParola().equals(parola)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Parola yanlış.");
        }

        return ResponseEntity.ok(kullanici);
    }

    @PostMapping("/kayit")
    public ResponseEntity<?> kullaniciKayit(@RequestBody Kullanici kullanici) {
        try {
            if (kullaniciRepository.existsByEmail(kullanici.getEmail())) {
                return ResponseEntity.badRequest().body("Bu e-posta zaten kayıtlı.");
            }
            kullanici.setKullaniciTuru("normal");
            kullaniciRepository.save(kullanici);
            return ResponseEntity.ok("Kayıt başarılı.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sunucu hatası.");
        }
    }

    @PostMapping("/tasarruf-ekle")
    public ResponseEntity<?> tasarrufEkle(@RequestBody Map<String, Object> data) {
        try {
            Integer kullaniciId = (Integer) data.get("kullanici_id");
            Double fark = Double.valueOf(data.get("fark").toString());

            Optional<Kullanici> opt = kullaniciRepository.findById(kullaniciId);
            if (opt.isEmpty()) return ResponseEntity.status(404).body("Kullanıcı bulunamadı");

            TasarrufKaydi kayit = new TasarrufKaydi();
            kayit.setKullaniciId(kullaniciId);
            kayit.setTutar(fark);
            tasarrufKaydiRepository.save(kayit);

            Kullanici k = opt.get();
            k.setToplamTasarruf((k.getToplamTasarruf() != null ? k.getToplamTasarruf() : 0) + fark);
            kullaniciRepository.save(k);

            return ResponseEntity.ok("Tasarruf eklendi");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Hata: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getKullanici(@PathVariable Integer id) {
        Optional<Kullanici> opt = kullaniciRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.status(404).body("Kullanıcı bulunamadı");

        Kullanici k = opt.get();
        Map<String, Object> result = new HashMap<>();
        result.put("kullanici_id", k.getKullaniciId());
        result.put("isim", k.getIsim());
        result.put("soyisim", k.getSoyisim());
        result.put("email", k.getEmail());
        result.put("kullanici_turu", k.getKullaniciTuru());
        result.put("toplamTasarruf", k.getToplamTasarruf());

        return ResponseEntity.ok(result);
    }


    @GetMapping("/{id}/aylik-tasarruf")
    public ResponseEntity<?> getAylikTasarruf(@PathVariable Integer id) {
        LocalDateTime birAyOnce = LocalDateTime.now().minusDays(30);
        List<TasarrufKaydi> kayitlar = tasarrufKaydiRepository.findByKullaniciIdAndTarihAfter(id, birAyOnce);
        double toplam = kayitlar.stream().mapToDouble(TasarrufKaydi::getTutar).sum();
        return ResponseEntity.ok(Map.of("aylikTasarruf", toplam));
    }

    @GetMapping("/{id}/gunluk-tasarruflar")
    public ResponseEntity<?> getGunlukTasarruflar(@PathVariable Integer id) {
        LocalDateTime birAyOnce = LocalDateTime.now().minusDays(30);
        List<TasarrufKaydi> kayitlar = tasarrufKaydiRepository.findByKullaniciIdAndTarihAfter(id, birAyOnce);

        Map<LocalDate, Double> gunlukMap = new TreeMap<>();
        for (TasarrufKaydi k : kayitlar) {
            LocalDate gun = k.getTarih().toLocalDate();
            gunlukMap.put(gun, gunlukMap.getOrDefault(gun, 0.0) + k.getTutar());
        }

        List<Map<String, Object>> sonuc = new ArrayList<>();
        for (int i = 0; i <= 30; i++) {
            LocalDate gun = LocalDate.now().minusDays(30 - i);
            Map<String, Object> veri = new HashMap<>();
            veri.put("tarih", gun.toString());
            veri.put("tutar", gunlukMap.getOrDefault(gun, 0.0));
            sonuc.add(veri);
        }

        return ResponseEntity.ok(sonuc);
    }

    @GetMapping("/{id}/grafik-verisi")
    public ResponseEntity<?> getTasarrufGrafikVerisi(@PathVariable Integer id) {
        LocalDateTime birAyOnce = LocalDateTime.now().minusDays(30);
        List<TasarrufKaydi> kayitlar = tasarrufKaydiRepository.findByKullaniciIdAndTarihAfter(id, birAyOnce);

        Map<String, Double> gunlukToplam = new TreeMap<>();
        for (TasarrufKaydi kayit : kayitlar) {
            String gun = kayit.getTarih().toLocalDate().toString();
            gunlukToplam.put(gun, gunlukToplam.getOrDefault(gun, 0.0) + kayit.getTutar());
        }

        return ResponseEntity.ok(gunlukToplam);
    }


}
