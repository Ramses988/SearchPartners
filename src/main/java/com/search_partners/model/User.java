package com.search_partners.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String password;
    private boolean enabled;
    private String initial;
    private String color;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Post> posts;

}
