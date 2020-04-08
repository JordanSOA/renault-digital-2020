package com.renault.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 16)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(nullable = false, length = 512)
    private String name;

    @OneToMany(mappedBy = "country",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY)
    private List<Region> regions;

    public Country() {
    }

    public Country(Language language, String name) {
        this.name = name;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Optional<Language> getLanguageOptional(){
        return Optional.ofNullable(language);
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", language=" + language +
                ", name='" + name + '\'' +
                '}';
    }

}
