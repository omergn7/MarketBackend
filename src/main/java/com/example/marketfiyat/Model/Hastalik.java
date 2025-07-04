package com.example.marketfiyat.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "hastalik")
public class Hastalik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "adi", nullable = false, unique = true)
    private String adi;

    public Hastalik() {
    }

    public Hastalik(Integer id, String adi) {
        this.id = id;
        this.adi = adi;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }
}
