package com.bestbuyplayground.testbase;

import com.bestbuyplayground.constants.Path;
import com.bestbuyplayground.utilis.PropertyReader;
import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class ServicesTestBase {
    public static PropertyReader propertyReader;

    @BeforeClass
    public static void init() {
        propertyReader = PropertyReader.getInstance();
        RestAssured.baseURI = propertyReader.getProperty("baseUrl");
        RestAssured.port = Integer.parseInt(propertyReader.getProperty("port"));
        RestAssured.basePath = Path.SERVICES;

    }
}
