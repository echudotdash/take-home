package APISteps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class APIBodyValidation {
    RequestSpecification request;
    Response response;
    public static String id;


    @Given("User Prepare the without query parameter")
    public void user_Prepare_the_without_query_parameter() {
        request = given().
                header(APIConstants.Header_Key_Content_Type, APIConstants.Header_Value_Content_Type);
    }


    @When("user send a GET call is made")
    public void user_send_a_GET_call_is_made() {
        response = request.when().get(APIConstants.BaseURI);
    }

    @Then("the status code will be {int}")
    public void the_status_code_will_be(Integer int1) {

        Integer statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, int1);

    }

    @Then("the response body contains id {string} and text {string}")
    public void the_response_body_contains_id_and_text(String string, String string2) {

        id = response.jsonPath().getString(string);
        Assert.assertTrue(response.getBody().asString().contains(id));
        Assert.assertTrue(response.getBody().asString().contains(string2));
    }

    @Then("the user can verify custom the game {string}")
    public void the_user_can_verify_custom_the_game(String game) {
        try {
            JSONObject json = new JSONObject(response.getBody().toString());
            Assert.assertTrue(json.get("text").equals("Playing " + game + " is fun!"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Then("the user verify the id is greater then zero")
    public void the_user_verify_the_id_is_greater_then_zero() {
        try {
            JSONObject json = new JSONObject(response.getBody().asString().toString());
            int idNew = Integer.parseInt(id);
            Assert.assertTrue((idNew > 0) ? true : false);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
