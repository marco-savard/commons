package com.marcosavard.webapp.service;

import java.io.Reader;

public class pojoGenServiceTest {

    public static void main(String[] args) {
        PojoGenService service = new PojoGenService();
        Reader reader = null;
        service.process(reader);
    }

}
