package com.example.marketfiyat.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Urun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int urunId;

    private String urunName;
    private String urunGorsel;
    private String urunIcerik;

    @ManyToOne
    @JoinColumn(name = "kategori_id")
    private Kategori kategori;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ulke_id")
    private Ulke ulke;


    @OneToOne(mappedBy = "urun", fetch = FetchType.EAGER)
    private BesinDegeri besinDegeri;



    @OneToMany(mappedBy = "urun", cascade = CascadeType.ALL)
    private List<Barkod> barkodlar;

    public int getUrunId() {
        return urunId;
    }

    public void setUrunId(int urunId) {
        this.urunId = urunId;
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

    public String getUrunIcerik() {
        return urunIcerik;
    }

    public void setUrunIcerik(String urunIcerik) {
        this.urunIcerik = urunIcerik;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }

    public Ulke getUlke() {
        return ulke;
    }

    public void setUlke(Ulke ulke) {
        this.ulke = ulke;
    }

    public BesinDegeri getBesinDegeri() {
        return besinDegeri;
    }

    public void setBesinDegeri(BesinDegeri besinDegeri) {
        this.besinDegeri = besinDegeri;
    }

    public List<Barkod> getBarkodlar() {
        return barkodlar;
    }

    public void setBarkodlar(List<Barkod> barkodlar) {
        this.barkodlar = barkodlar;
    }
}
