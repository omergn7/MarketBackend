package com.example.marketfiyat.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Kategori {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int kategoriId;

    public int getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(int kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getKategoriAdi() {
        return kategoriAdi;
    }

    public void setKategoriAdi(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }

    private String kategoriAdi;
}
