package com.search_partners.model;

public enum Provider {

    LOCAL("local"),
    GOOGLE("google"),
    FACEBOOK("facebook"),
    VK("vk");

    private final String name;

    Provider(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
