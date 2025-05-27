package com.example.marketfiyat.Controller;
import com.example.marketfiyat.DTO.MarketDTO;
import com.example.marketfiyat.Model.Market;
import com.example.marketfiyat.Repository.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/marketler")
public class MarketController {

    @Autowired
    private MarketRepository marketRepository;

    @GetMapping
    public List<MarketDTO> getAllMarkets() {
        return marketRepository.findAll()
                .stream()
                .map(m -> new MarketDTO(
                        m.getMarketId(),
                        m.getMarketName(),
                        m.getMarketLocation(),
                        m.getMarketGorsel()
                ))
                .collect(Collectors.toList());
    }
}
