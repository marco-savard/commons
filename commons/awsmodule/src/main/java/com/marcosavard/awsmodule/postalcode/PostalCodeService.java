package com.marcosavard.awsmodule.postalcode;

import java.io.IOException;

public interface PostalCodeService {
    String[] findPostalCode(String postalcode) throws IOException;
}
