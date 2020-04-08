package com.renault.dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

// { country: { "name", ...
public class CountryRegionDto {

    @NotNull
    @Valid
    private CountryDto country;

    @NotNull
    @Valid
    private RegionDto region;

    public CountryDto getCountry() {
        return country;
    }

    public void setCountry(CountryDto country) {
        this.country = country;
    }

    public RegionDto getRegion() {
        return region;
    }

    public void setRegion(RegionDto region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "CountryRegionDto{" +
                "country=" + country +
                ", region=" + region +
                '}';
    }

}
