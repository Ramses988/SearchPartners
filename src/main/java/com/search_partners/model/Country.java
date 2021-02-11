package com.search_partners.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    public Country() {}

    public Country(long id) {
        this.id = id;
    }
}
