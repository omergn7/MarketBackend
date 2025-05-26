package com.example.marketfiyat.Controller;

import com.example.marketfiyat.Model.Kullanici;
import com.example.marketfiyat.Repository.KullaniciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/kullanici")
public class KullaniciController {

    @Autowired
    private KullaniciRepository kullaniciRepository;

    // 📥 Giriş endpoint'i
    @PostMapping("/giris")
    public ResponseEntity<?> kullaniciGiris(@RequestBody Map<String, String> login) {
        String email = login.get("email");
        String parola = login.get("parola");

        System.out.println("🔍 Gelen email: " + email);
        System.out.println("🔍 Gelen parola: " + parola);

        Optional<Kullanici> kullaniciOpt = kullaniciRepository.findByEmail(email);
        if (kullaniciOpt.isEmpty()) {
            System.out.println("❌ Kullanıcı bulunamadı!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Kullanıcı bulunamadı.");
        }

        Kullanici kullanici = kullaniciOpt.get();
        System.out.println("✅ Kullanıcı bulundu: " + kullanici.getEmail());

        if (!kullanici.getParola().equals(parola)) {
            System.out.println("❌ Parola eşleşmedi!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Parola yanlış.");
        }

        System.out.println("✅ Giriş başarılı!");
        return ResponseEntity.ok(kullanici);
    }

    // 📝 Kayıt endpoint'i
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
}
