package com.search_partners.model.abstractentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.search_partners.model.City;
import com.search_partners.model.Comment;
import com.search_partners.model.Country;
import com.search_partners.model.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AbstractBasePost extends AbstractBaseEntity {

    private String title;
    private String text;
    private LocalDateTime date;
    private long show;
    private long comments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    private int active;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @Transient
    private String duration;
    @Transient
    private String durationShort;

}
