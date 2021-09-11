package com.search_partners.to;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class EmailMessage {

    private String to;
    private String subject;
    private String name;
    private String templateLocation;
    private Map<String, Object> context;

}
