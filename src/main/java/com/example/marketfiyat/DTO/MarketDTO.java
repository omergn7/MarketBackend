package com.example.marketfiyat.DTO;

public class MarketDTO {
    private int marketId;
    private String marketName;
    private String marketLocation;
    private String marketGorsel;

    public MarketDTO(int marketId, String marketName, String marketLocation, String marketGorsel) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.marketLocation = marketLocation;
        this.marketGorsel = marketGorsel;
    }

    public int getMarketId() {
        return marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public String getMarketLocation() {
        return marketLocation;
    }

    public String getMarketGorsel() {
        return marketGorsel;
    }
}
