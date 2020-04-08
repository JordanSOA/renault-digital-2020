package com.renault.services;

import com.renault.dtos.CityDto;
import com.renault.entities.City;
import com.renault.entities.Country;
import com.renault.entities.Region;

import java.util.List;
import java.util.Optional;

public interface CityService {

    Optional<City> getCity(int id);

    void save(City city);

    void save(Country country, Region region, City city);

    List<CityDto> deleteCity(City city);

    List<City> findByName(List<String> cities);
}
