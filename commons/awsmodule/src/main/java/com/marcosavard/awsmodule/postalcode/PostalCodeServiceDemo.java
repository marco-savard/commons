package com.marcosavard.awsmodule.postalcode;

import java.io.IOException;

public class PostalCodeServiceDemo {

    public static void main(String[] args) {
        try {
            PostalCodeService postalCodeService = new PostalCodeServiceImpl();
            String[] info = postalCodeService.findPostalCode("G3E2C7");
            System.out.println("{" + String.join("; ", info) + "}");
            System.out.println("Success");
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
