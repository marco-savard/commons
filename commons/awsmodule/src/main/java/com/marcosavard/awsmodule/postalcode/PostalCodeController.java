package com.marcosavard.awsmodule.postalcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PostalCodeController {
    private final PostalCodeService postalCodeService;

    @Autowired
    public PostalCodeController(PostalCodeService postalCodeService) {
        this.postalCodeService = postalCodeService;
        System.out.println("PostalCodeController");
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/postalcode")
    public String postalcode() {
        return "Request should be /postalcode/{postalcode} where postalcode are g0a0a0 to j9z9z9";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/postalcode/{postalcode}")
    public String postalcodePostalCode(@PathVariable("postalcode") String postalcode) {
        String result;

        try {
            String[] words = postalCodeService.findPostalCode(postalcode);
            result = "{" + String.join("; ", words) + "}";
        } catch (IOException e) {
            result = e.getMessage();
        }

        return result;
    }

}
