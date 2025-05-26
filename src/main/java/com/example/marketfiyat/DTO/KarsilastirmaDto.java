package com.example.marketfiyat.DTO;

import lombok.Data;

import java.util.List;

public class KarsilastirmaDto {
    private String urunAdi;
    private String barkod;
    private List<PazarFiyati> karsilastirma;

    @Data
    public static class PazarFiyati {
        private String market;

        public double getFiyat() {
            return fiyat;
        }

        public void setFiyat(double fiyat) {
            this.fiyat = fiyat;
        }

        public String getMarket() {
            return market;
        }

        public void setMarket(String market) {
            this.market = market;
        }

        public Long getBarkodId() {
            return barkodId;
        }

        public void setBarkodId(Long barkodId) {
            this.barkodId = barkodId;
        }

        private double fiyat;
        private Long barkodId;
    }

}
