package Steps;

import Utils.Data;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

public class GameTests {

        String baseURI = RestAssured.baseURI= "http://localhost:8080";
        static RequestSpecification requestSpecification;
        static Response response;
        static int currentId=0;
        static int previousId=0;
    @Given("request is prepared for the default endpoint")
    public void request_is_prepared_for_the_default_endpoint() {
        requestSpecification = given()
                .queryParam("name","");
    }
    @When("call to the default endpoint is made")
    public void call_to_the_endpoint_is_made() {
        response = requestSpecification.when().get("/game");
        Data.jsonResponseMap=response.jsonPath().getMap("");
        System.out.println(Data.jsonResponseMap);
    }
    @Then("the returned name for the game must be {string}")
    public void the_returned_name_for_the_game_must_be(String gameName) {

        response.then().assertThat().statusCode(200);

        response.then().assertThat().body("text", containsString(gameName));

        currentId = (int) Data.jsonResponseMap.get("id");
    }
    @Then("the id should have increased")
    public void the_id_should_have_increased() {
       Assert.assertTrue(currentId > previousId);
       previousId = currentId;
    }
}