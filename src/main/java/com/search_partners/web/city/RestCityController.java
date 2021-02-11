package com.search_partners.web.city;

import com.search_partners.model.City;
import com.search_partners.service.CountryAndCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = RestCityController.REST_URL)
public class RestCityController {

    static final String REST_URL = "/rest/country";
    private final CountryAndCityService service;

    @Autowired
    public RestCityController(CountryAndCityService service) {
        this.service = service;
    }

    @PostMapping("/get-cities")
    public List<City> getCities(@RequestParam int id) {
        return service.getCities(id);
    }

}
