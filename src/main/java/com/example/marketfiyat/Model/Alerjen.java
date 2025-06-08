package com.example.marketfiyat.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alerjen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alerjenId;

    private String alerjenAdi;

    @ManyToMany(mappedBy = "alerjenler")
    @JsonIgnore // bu önemli!
    private Set<Urun> urunler;


    public Long getAlerjenId() {
        return alerjenId;
    }

    public void setAlerjenId(Long alerjenId) {
        this.alerjenId = alerjenId;
    }

    public Set<Urun> getUrunler() {
        return urunler;
    }

    public void setUrunler(Set<Urun> urunler) {
        this.urunler = urunler;
    }

    public String getAlerjenAdi() {
        return alerjenAdi;
    }

    public void setAlerjenAdi(String alerjenAdi) {
        this.alerjenAdi = alerjenAdi;
    }
}
