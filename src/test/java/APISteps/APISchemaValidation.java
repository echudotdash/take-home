package APISteps;

import io.cucumber.java.en.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;

import static io.restassured.RestAssured.given;

public class APISchemaValidation {
    RequestSpecification request;
    Response response1;

    @Given("user prepare the request with Query key {string} and query parameter {string}")
    public void user_prepare_the_request_with_Query_key_and_query_parameter(String string, String string2) {
        request = request = given().
                header(APIConstants.Header_Key_Content_Type, APIConstants.Header_Value_Content_Type)
                .queryParam(string, string2);
    }

    @When("user send GET call with Query Parameter")
    public void user_send_GET_call_with_Query_Parameter() {
        response1 = request.when().get(APIConstants.BaseURI);
    }
    @Then("User should validate the JSON Schema")
    public void user_should_validate_the_JSON_Schema() {
        response1.then().assertThat()
                .body(JsonSchemaValidator.
                        matchesJsonSchema(new File(APIConstants.PATH_TO_SCHEMA)))
                .statusCode(200);

    }

}
