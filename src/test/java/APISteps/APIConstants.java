package APISteps;

import io.restassured.RestAssured;

public class APIConstants {

    public static final String BaseURI = RestAssured.baseURI = "http://localhost:8080/game";
    public static final String Header_Key_Content_Type = "Content-Type";
    public static final String Header_Value_Content_Type = "application/json";
    public static final String PATH_TO_SCHEMA = "src/test/resources/Data/Schema.json";
}
