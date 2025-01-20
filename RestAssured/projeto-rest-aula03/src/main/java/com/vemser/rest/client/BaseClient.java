package com.vemser.rest.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public abstract class BaseClient {

    final String BASE_URI = "http://localhost:3000";
    public RequestSpecification set() {

            return new RequestSpecBuilder()
                    .setBaseUri(BASE_URI)
                    .setContentType(ContentType.JSON)
                    .setConfig(RestAssuredConfig.config().logConfig(
                            LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                    .build()
                    ;

    }

}
