package com.renault.repositories;

import com.renault.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    @Query("SELECT c FROM Country c WHERE c.name =:name")
    Country findbyName(@Param("name") String name);
}
