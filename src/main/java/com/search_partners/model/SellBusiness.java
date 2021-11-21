package com.search_partners.model;

import com.search_partners.model.abstractentity.AbstractBasePost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sell_business")
public class SellBusiness extends AbstractBasePost {

    private long price;
    private String age;
    private long profit;
    private long income;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<CommentSell> commentList;

}
