package com.example.marketfiyat.DTO;

public class UrunDetayDto {
    private String urunName;
    private String urunGorsel;
    private String urunIcerik;
    private String ulkeAdi;
    private String kategoriAdi;

    // Besin değerleri
    private Double enerjiKcal;
    private Double yag;
    private Double doymusYag;
    private Double karbonhidrat;
    private Double seker;
    private Double protein;
    private Double tuz;

    // --- Constructor ---
    public UrunDetayDto(String urunName, String urunGorsel, String urunIcerik,
                        String ulkeAdi, String kategoriAdi,
                        Double enerjiKcal, Double yag, Double doymusYag,
                        Double karbonhidrat, Double seker, Double protein, Double tuz) {
        this.urunName = urunName;
        this.urunGorsel = urunGorsel;
        this.urunIcerik = urunIcerik;
        this.ulkeAdi = ulkeAdi;
        this.kategoriAdi = kategoriAdi;
        this.enerjiKcal = enerjiKcal;
        this.yag = yag;
        this.doymusYag = doymusYag;
        this.karbonhidrat = karbonhidrat;
        this.seker = seker;
        this.protein = protein;
        this.tuz = tuz;
    }

    // --- Getter ve Setter’lar ---
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

    public String getUrunIcerik() {
        return urunIcerik;
    }

    public void setUrunIcerik(String urunIcerik) {
        this.urunIcerik = urunIcerik;
    }

    public String getUlkeAdi() {
        return ulkeAdi;
    }

    public void setUlkeAdi(String ulkeAdi) {
        this.ulkeAdi = ulkeAdi;
    }

    public String getKategoriAdi() {
        return kategoriAdi;
    }

    public void setKategoriAdi(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }

    public Double getEnerjiKcal() {
        return enerjiKcal;
    }

    public void setEnerjiKcal(Double enerjiKcal) {
        this.enerjiKcal = enerjiKcal;
    }

    public Double getYag() {
        return yag;
    }

    public void setYag(Double yag) {
        this.yag = yag;
    }

    public Double getDoymusYag() {
        return doymusYag;
    }

    public void setDoymusYag(Double doymusYag) {
        this.doymusYag = doymusYag;
    }

    public Double getKarbonhidrat() {
        return karbonhidrat;
    }

    public void setKarbonhidrat(Double karbonhidrat) {
        this.karbonhidrat = karbonhidrat;
    }

    public Double getSeker() {
        return seker;
    }

    public void setSeker(Double seker) {
        this.seker = seker;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getTuz() {
        return tuz;
    }

    public void setTuz(Double tuz) {
        this.tuz = tuz;
    }
}
