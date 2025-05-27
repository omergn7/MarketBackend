package com.example.marketfiyat.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "market_urun")
public class MarketUrun {

    @Id
    @Column(name = "market_urun_id") // veritabanındaki gerçek sütun adını kullan
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "market_id")
    @JsonBackReference
    private Market market;

    @ManyToOne
    @JoinColumn(name = "barkod_id")
    private Barkod barkod;

    private Double fiyat;

    @Column(name = "fiyat_guncelleme_tarihi")
    private Date fiyatGuncellemeTarihi;

    // GETTER / SETTER

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public Barkod getBarkod() {
        return barkod;
    }

    public void setBarkod(Barkod barkod) {
        this.barkod = barkod;
    }

    public Double getFiyat() {
        return fiyat;
    }

    public void setFiyat(Double fiyat) {
        this.fiyat = fiyat;
    }

    public Date getFiyatGuncellemeTarihi() {
        return fiyatGuncellemeTarihi;
    }

    public void setFiyatGuncellemeTarihi(Date fiyatGuncellemeTarihi) {
        this.fiyatGuncellemeTarihi = fiyatGuncellemeTarihi;
    }
}
