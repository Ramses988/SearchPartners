package com.search_partners.model;

import com.search_partners.model.abstractentity.AbstractBasePost;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="posts")
public class Post extends AbstractBasePost {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

}