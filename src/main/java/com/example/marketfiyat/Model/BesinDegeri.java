package com.example.marketfiyat.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BesinDegeri {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int besinDegeriId;

    @OneToOne
    @JoinColumn(name = "urun_id")
    private Urun urun;

    private Integer enerjiKcal;
    private Integer enerjiKj;
    private Double yag;
    private Double doymusYag;
    private Double transYag;
    private Double karbonhidrat;
    private Double seker;
    private Double lif;
    private Double protein;
    private Double tuz;
    private Double sodyum;

    // Getter ve Setter'lar ↓

    public int getBesinDegeriId() {
        return besinDegeriId;
    }

    public void setBesinDegeriId(int besinDegeriId) {
        this.besinDegeriId = besinDegeriId;
    }

    public Urun getUrun() {
        return urun;
    }

    public void setUrun(Urun urun) {
        this.urun = urun;
    }

    public Integer getEnerjiKcal() {
        return enerjiKcal;
    }

    public void setEnerjiKcal(Integer enerjiKcal) {
        this.enerjiKcal = enerjiKcal;
    }

    public Integer getEnerjiKj() {
        return enerjiKj;
    }

    public void setEnerjiKj(Integer enerjiKj) {
        this.enerjiKj = enerjiKj;
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

    public Double getTransYag() {
        return transYag;
    }

    public void setTransYag(Double transYag) {
        this.transYag = transYag;
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

    public Double getLif() {
        return lif;
    }

    public void setLif(Double lif) {
        this.lif = lif;
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

    public Double getSodyum() {
        return sodyum;
    }

    public void setSodyum(Double sodyum) {
        this.sodyum = sodyum;
    }
}
