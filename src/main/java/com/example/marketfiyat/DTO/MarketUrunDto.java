package com.example.marketfiyat.DTO;

public class MarketUrunDto {
    private int barkodId;
    private String urunName;
    private String urunGorsel;
    private Double fiyat;
    private String ulkeAdi;

    public String getUlkeAdi() {
        return ulkeAdi;
    }

    public void setUlkeAdi(String ulkeAdi) {
        this.ulkeAdi = ulkeAdi;
    }

    public int getBarkodId() {
        return barkodId;
    }

    public void setBarkodId(int barkodId) {
        this.barkodId = barkodId;
    }

    public String getUrunName() {
        return urunName;
    }

    public void setUrunName(String urunName) {
        this.urunName = urunName;
    }

    public String getUrunGorsel() {
        return urunGorsel;
    }

    public void setUrunGorsel(String urunGorsel) {
        this.urunGorsel = urunGorsel;
    }

    public Double getFiyat() {
        return fiyat;
    }

    public void setFiyat(Double fiyat) {
        this.fiyat = fiyat;
    }

    public MarketUrunDto(int barkodId, String urunName, String urunGorsel, Double fiyat, String ulkeAdi) {
        this.barkodId = barkodId;
        this.urunName = urunName;
        this.urunGorsel = urunGorsel;
        this.fiyat = fiyat;
        this.ulkeAdi = ulkeAdi;
    }

    // Getter'lar
}
