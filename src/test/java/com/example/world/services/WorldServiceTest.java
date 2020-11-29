package com.example.world.services;

import com.example.world.dto.CountryDto;
import com.example.world.exceptions.custom.NotFoundException;
import com.example.world.models.Country;
import com.example.world.models.CountryLanguage;
import com.example.world.models.CountryLanguageId;
import com.example.world.repositories.CountryLanguageRepository;
import com.example.world.repositories.CountryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class WorldServiceTest {
    @Mock
    CountryRepository countryRepository;
    @Mock
    CountryLanguageRepository countryLanguageRepository;
    @InjectMocks
    WorldService worldService;
    private Country country;
    private CountryLanguage countryLanguage;

    @Before
    public void init() {
        country = new Country();
        country.setCode("BHR");
        country.setName("kasj");
        country.setContinent("aks");
        country.setLifeExpectancy(12);
        countryLanguage = new CountryLanguage();
        countryLanguage.setCountryLanguageId(new CountryLanguageId(country, "as"));
    }

    @Test(expected = Exception.class)
    public void getCountryByCodeTest1() throws Exception {
        Mockito.when(countryRepository.findByCode("123")).
                thenReturn(null);
        CountryDto countryDto = worldService.getCountryByCode("123");
        Exception exception = assertThrows(NotFoundException.class, () -> {
            Integer.parseInt("1a");
        });
        Assert.assertTrue(exception.getMessage().equals("INVALID_COUNTRY_CODE"));
    }


    @Test(expected = Exception.class)
    public void getCountryByCodeTest2() throws Exception {
        Mockito.when(countryRepository.findByCode("123")).
                thenReturn(country);
        Mockito.when(countryLanguageRepository.findByCountryLanguageId_CountryCode(country)).thenReturn(null);
        CountryDto countryDto = worldService.getCountryByCode("123");
        Exception exception = assertThrows(NotFoundException.class, () -> {
            Integer.parseInt("1a");
        });
        Assert.assertTrue(exception.getMessage().equals("INVALID_COUNTRY_CODE"));
    }

    @Test
    public void getCountryByCodeTest3() throws Exception {
        Mockito.when(countryRepository.findByCode("123")).
                thenReturn(country);
        Mockito.when(countryLanguageRepository.findByCountryLanguageId_CountryCode(country)).thenReturn(countryLanguage);
        CountryDto countryDto = worldService.getCountryByCode("123");
        Assert.assertEquals(countryDto.getName(), country.getName());
        Assert.assertEquals(countryDto.getContinent(), country.getContinent());
        Assert.assertEquals(countryDto.getLanguage(), countryLanguage.getCountryLanguageId().getLanguage());
    }

    @Test(expected = Exception.class)
    public void getCountryByCodeTest4() throws Exception {
        Mockito.when(countryRepository.findByCode("123")).
                thenThrow(new Exception());
        Mockito.when(countryLanguageRepository.findByCountryLanguageId_CountryCode(country)).thenReturn(countryLanguage);
        CountryDto countryDto = worldService.getCountryByCode("123");
        Exception exception = assertThrows(Exception.class, () -> {
            Integer.parseInt("1a");
        });
        Assert.assertTrue(exception.getMessage().equals("INTERNAL_ERROR"));


    }

    @Test(expected = Exception.class)
    public void getCountryByCodeTest5() throws Exception {
        Mockito.when(countryRepository.findByCode("123")).
                thenReturn(country);
        Mockito.when(countryLanguageRepository.findByCountryLanguageId_CountryCode(country)).thenThrow(new Exception());
        CountryDto countryDto = worldService.getCountryByCode("123");
        Exception exception = assertThrows(Exception.class, () -> {
            Integer.parseInt("1a");
        });
        Assert.assertTrue(exception.getMessage().equals("INTERNAL_ERROR"));


    }
}