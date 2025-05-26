package com.example.marketfiyat.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Barkod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int barkodId;

    private String barkod;

    @ManyToOne(fetch = FetchType.EAGER) // LAZY ise değiştir!
    @JoinColumn(name = "urun_id")
    private Urun urun;

    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;

    public int getBarkodId() {
        return barkodId;
    }

    public void setBarkodId(int barkodId) {
        this.barkodId = barkodId;
    }

    public String getBarkod() {
        return barkod;
    }

    public void setBarkod(String barkod) {
        this.barkod = barkod;
    }

    public Urun getUrun() {
        return urun;
    }

    public void setUrun(Urun urun) {
        this.urun = urun;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }
}
