package com.search_partners.model;

import com.search_partners.model.abstractentity.AbstractBaseEntity;
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

    @Column(name = "name_en")
    private String nameEn;

    public Country(long id) {
        super(id);
    }

}