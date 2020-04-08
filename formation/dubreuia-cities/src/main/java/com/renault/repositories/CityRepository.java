package com.renault.repositories;

import com.renault.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    /*
    @Query("SELECT c FROM Country c WHERE c.name =:name")
    Country findbyName(@Param("name") String name);
     */
    @Query("SELECT c FROM City c WHERE c.name=:name")
    City findByName(@Param("name") String name);
}
