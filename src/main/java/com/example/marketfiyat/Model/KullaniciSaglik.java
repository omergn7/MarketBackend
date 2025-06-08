package com.example.marketfiyat.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "kullanici_saglik")
public class KullaniciSaglik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "kullanici_id", nullable = false)
    private Kullanici kullanici;

    @Column(name = "tip", nullable = false) // "hastalik" veya "alerjen"
    private String tip;

    @Column(name = "adi", nullable = false)
    private String adi; // örnek: "gluten", "kalp", "laktoz"

    public KullaniciSaglik() {}

    public KullaniciSaglik(Integer id, Kullanici kullanici, String tip, String adi) {
        this.id = id;
        this.kullanici = kullanici;
        this.tip = tip;
        this.adi = adi;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }
}
