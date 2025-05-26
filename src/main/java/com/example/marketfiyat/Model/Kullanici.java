package com.example.marketfiyat.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Kullanici {
    private String isim;
    private String soyisim;
    private String email;
    private String parola;
    private String kullaniciTuru;
    @Column(name = "toplam_tasarruf")
    private Double toplamTasarruf = 0.0;

    @Column(name = "aylik_tasarruf")
    private Double aylikTasarruf = 0.0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer kullaniciId;

    public Integer getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(Integer kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getKullaniciTuru() {
        return kullaniciTuru;
    }

    public void setKullaniciTuru(String kullaniciTuru) {
        this.kullaniciTuru = kullaniciTuru;
    }


    public Double getToplamTasarruf() { return toplamTasarruf; }
    public void setToplamTasarruf(Double toplamTasarruf) { this.toplamTasarruf = toplamTasarruf; }

    public Double getAylikTasarruf() { return aylikTasarruf; }
    public void setAylikTasarruf(Double aylikTasarruf) { this.aylikTasarruf = aylikTasarruf; }



}
