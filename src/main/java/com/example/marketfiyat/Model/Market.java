package com.example.marketfiyat.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int marketId;

    private String marketName;
    private String marketLocation;

    private String marketGorsel;  // ✅ yeni eklenen sütun

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL)
    private List<Barkod> barkodlar;

    @OneToMany(mappedBy = "market")
    @JsonManagedReference
    private List<MarketUrun> marketUrunleri;

    // --- Getter ve Setter'lar ---

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMarketLocation() {
        return marketLocation;
    }

    public void setMarketLocation(String marketLocation) {
        this.marketLocation = marketLocation;
    }

    public String getMarketGorsel() {
        return marketGorsel;
    }

    public void setMarketGorsel(String marketGorsel) {
        this.marketGorsel = marketGorsel;
    }

    public List<Barkod> getBarkodlar() {
        return barkodlar;
    }

    public void setBarkodlar(List<Barkod> barkodlar) {
        this.barkodlar = barkodlar;
    }

    public List<MarketUrun> getMarketUrunleri() {
        return marketUrunleri;
    }

    public void setMarketUrunleri(List<MarketUrun> marketUrunleri) {
        this.marketUrunleri = marketUrunleri;
    }
}
