package com.example.marketfiyat.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int marketId;

    private String marketName;
    private String marketLocation;

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL)
    private List<Barkod> barkodlar;

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

    public List<Barkod> getBarkodlar() {
        return barkodlar;
    }

    public void setBarkodlar(List<Barkod> barkodlar) {
        this.barkodlar = barkodlar;
    }
}
