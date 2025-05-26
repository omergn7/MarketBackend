package com.example.marketfiyat.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Ulke {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ulkeId;

    public String getUlkeAdi() {
        return ulkeAdi;
    }

    public void setUlkeAdi(String ulkeAdi) {
        this.ulkeAdi = ulkeAdi;
    }

    public int getUlkeId() {
        return ulkeId;
    }

    public void setUlkeId(int ulkeId) {
        this.ulkeId = ulkeId;
    }

    private String ulkeAdi;
}