package com.search_partners.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="countries")
public class Country extends AbstractBaseEntity {

    private String name;

    public Country(long id) {
        super(id);
    }
}