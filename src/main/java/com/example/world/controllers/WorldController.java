package com.example.world.controllers;

import com.example.world.services.WorldService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class WorldController {
    private final WorldService worldService;

    public WorldController(WorldService worldService) {
        this.worldService = worldService;
    }

    @GetMapping("{countryCode}")
    public ResponseEntity<?> getCountryByCode(@PathVariable String countryCode) throws Exception {
        return ResponseEntity.ok(worldService.getCountryByCode(countryCode.toUpperCase()));
    }

    @GetMapping("get-all-countries")
    public ResponseEntity<?> getAllCountry() {
        return ResponseEntity.ok(worldService.getAllCountry());
    }
}
