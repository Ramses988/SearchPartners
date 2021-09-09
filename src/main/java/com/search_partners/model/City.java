package com.search_partners.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cities")
public class City extends AbstractBaseEntity {

    private String name;

    @Column(name = "name_en")
    private String nameEn;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    public City(long id, String name, String nameEn) {
        super(id);
        this.name = name;
        this.nameEn = nameEn;
    }
}