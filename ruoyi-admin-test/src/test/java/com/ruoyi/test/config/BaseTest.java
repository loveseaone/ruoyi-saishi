package com.ruoyi.test.config;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseTest {
    protected static String baseURI;
    protected static String prefix;
    protected static Integer basePORT;
    protected static String X_ACCESS_TOKEN;

    @BeforeAll
    public static void setUp() throws IOException {
        InputStream inputStream = BaseTest.class.getClassLoader().getResourceAsStream("application.properties");
        Properties props = new Properties();
        props.load(inputStream);


        baseURI = props.getProperty("base.url");
        basePORT = Integer.parseInt(props.getProperty("base.port"));
        prefix = props.getProperty("base.prefix");

        X_ACCESS_TOKEN = props.getProperty("auth.token");

    }

    @BeforeEach
    public void init() {
        RestAssured.baseURI = baseURI;
        RestAssured.port = basePORT;
    }

}
