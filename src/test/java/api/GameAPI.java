package api;


import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class GameAPI {

    private static final String baseUri = "http://localhost:8080";

    public static Response getSpecificGameDetails(String gameName) {

        return given().baseUri(baseUri).queryParam("name", gameName).get("/game");

    }

    public static Response getDefaultGameDetails() {

        return given().baseUri(baseUri).get("/game");

    }
}
