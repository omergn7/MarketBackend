package com.example.marketfiyat.DTO;

import java.util.List;

public class KarsilastirmaDto {
    private String urunAdi;
    private String barkod;
    private String urunGorsel; // Ürün görseli eklendi
    private String ulke;       // Ülke bilgisi eklendi
    private String bayrak;     // Emoji olarak bayrak eklendi

    private List<PazarFiyati> karsilastirma;

    // Getter ve Setter
    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public String getBarkod() {
        return barkod;
    }

    public void setBarkod(String barkod) {
        this.barkod = barkod;
    }

    public String getUrunGorsel() {
        return urunGorsel;
    }

    public void setUrunGorsel(String urunGorsel) {
        this.urunGorsel = urunGorsel;
    }

    public String getUlke() {
        return ulke;
    }

    public void setUlke(String ulke) {
        this.ulke = ulke;
    }

    public String getBayrak() {
        return bayrak;
    }

    public void setBayrak(String bayrak) {
        this.bayrak = bayrak;
    }

    public List<PazarFiyati> getKarsilastirma() {
        return karsilastirma;
    }

    public void setKarsilastirma(List<PazarFiyati> karsilastirma) {
        this.karsilastirma = karsilastirma;
    }

    public static class PazarFiyati {
        private String market;
        private double fiyat;
        private Long barkodId;
        private String marketGorsel; // Market görseli

        public String getMarket() {
            return market;
        }

        public void setMarket(String market) {
            this.market = market;
        }

        public double getFiyat() {
            return fiyat;
        }

        public void setFiyat(double fiyat) {
            this.fiyat = fiyat;
        }

        public Long getBarkodId() {
            return barkodId;
        }

        public void setBarkodId(Long barkodId) {
            this.barkodId = barkodId;
        }

        public String getMarketGorsel() {
            return marketGorsel;
        }

        public void setMarketGorsel(String marketGorsel) {
            this.marketGorsel = marketGorsel;
        }
    }
}
