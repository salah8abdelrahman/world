package com.example.world.services;

import com.example.world.dto.CountryDto;
import com.example.world.exceptions.custom.NotFoundException;
import com.example.world.models.Country;
import com.example.world.models.CountryLanguage;
import com.example.world.repositories.CityRepository;
import com.example.world.repositories.CountryLanguageRepository;
import com.example.world.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class WorldService {
    private final CountryRepository countryRepository;
    private final CountryLanguageRepository countryLanguageRepository;

    @Autowired
    public WorldService(CountryRepository countryRepository, CountryLanguageRepository countryLanguageRepository) {

        this.countryRepository = countryRepository;
        this.countryLanguageRepository = countryLanguageRepository;
    }

    public CountryDto getCountryByCode(String countryCode) throws Exception {
        try {
            Country country = countryRepository.findByCode(countryCode);
            if (country == null) {
                throw new NotFoundException("INVALID_COUNTRY_CODE");
            }
            CountryLanguage countryLanguage = countryLanguageRepository
                    .findByCountryLanguageId_CountryCode(country);
            if (countryLanguage == null) {
                throw new NotFoundException("INVALID_COUNTRY_CODE");
            }
            return new CountryDto(country.getName(),
                    country.getContinent(), country.getPopulation(),
                    country.getLifeExpectancy(),
                    countryLanguage.getCountryLanguageId().getLanguage());
        } catch (Exception exception) {
            throw new Exception();
        }
    }

    public ArrayList<CountryDto> getAllCountry() {
        ArrayList<CountryDto> list = new ArrayList<>();
        CountryDto c = new CountryDto("counrty1",
                "Continent1", 123, 123.0, "asala");
        CountryDto c2 = new CountryDto("counrty2",
                "Continent1", 123, 123.0, "asala");
        list.add(c);
        list.add(c2);
        return list;
    }

}
