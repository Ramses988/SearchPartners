package com.search_partners.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="posts")
public class Post extends AbstractBasePost {
}