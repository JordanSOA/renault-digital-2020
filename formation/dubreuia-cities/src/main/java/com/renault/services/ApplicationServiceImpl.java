package com.renault.services;

import com.renault.entities.*;
import com.renault.repositories.CityRepository;
import com.renault.repositories.CountryRepository;
import com.renault.repositories.RegionRepository;
import com.renault.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public void deleteAll() {
        userRepository.deleteAll();
        cityRepository.deleteAll();
        regionRepository.deleteAll();
        countryRepository.deleteAll();
    }

    @Override
    @Transactional
    public void insertData() {
        Country france = new Country(null, "France");
        Country canada = new Country(Language.EN, "Canada");
        Country japan = new Country(Language.JA, "Japan");

        countryRepository.save(france);
        countryRepository.save(canada);
        countryRepository.save(japan);

        Region idf = new Region("IDF", france);
        Region quebec = new Region("Québec", canada);
        regionRepository.save(idf);
        regionRepository.save(quebec);
        City paris = new City("Paris", idf);
        City montreal = new City("Montréal", quebec);

        cityRepository.save(paris);
        cityRepository.save(new City("Montreuil", idf));
        cityRepository.save(montreal);
        cityRepository.save(new City("Laval", quebec));
        //cityRepository.save(new City("Evry", idf));
        User user = new User("Alex", List.of(paris, montreal));
        userRepository.save(user);
    }

}
