package com.search_partners.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="users")
public class User extends AbstractBaseEntity {

    private long id;
    private String name;
    private LocalDateTime date;
    @Column(name = "real_name")
    private String realName;
    private String email;
    private String password;
    private boolean enabled;
    private String initial;
    private String color;
    private String gender;
    private int busyness;
    private int day;
    private int month;
    private int year;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Post> posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    public User() {}

    public User(String name, LocalDateTime date, String email, boolean enabled, String initial, String color, String gender, Role... rolesSet) {
        this.name = name;
        this.date = date;
        this.email = email;
        this.enabled = enabled;
        this.initial = initial;
        this.color = color;
        this.gender = gender;
        setRoles(Set.of(rolesSet));
    }
}