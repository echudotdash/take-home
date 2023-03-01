package fun;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import pojo.pojodata;  //created a data class to store/parse object and imported here to read the values.
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
public class GameTests {

	public final static String Base_Url="http://localhost:8080/game?name=";
	@Test
	public void test_with_params()  //Scenario 1: Testing by different games as parameters should return the corresponding id and the text should contain the game name
	{
		String queryparams[]= {"Chess","Carom","Hockey"}; // Declared different games in an array to check if the string and id is getting returned based on games
		for(String game:queryparams)
		{
			Response response= get(Base_Url+game); // appended query parameter with base uri
			response.then()
			.log().all()
			.body("text",equalTo ("Playing " +game+ " is fun!"))
			.statusCode(200)
			.statusLine("HTTP/1.1 200 ")
			.contentType("application/json");
			pojodata dataclass= response.as(pojodata.class);  //JSON to POJO (created data class and used it to store/parse the JSON)
	        assertNotNull(dataclass.getId());
	        assertTrue(dataclass.getText().contains(game));		//verified the return value contains game name using Junit assertion
			
		}
	}
	@Test
		public void test_without_params()    // Scenario 2: Testing without passing query parameter should return the id and default game name Sudoku
		{
			String defaultgame="Sudoku";
				Response response= get(Base_Url); //passed base uri without passing any query parameter
				response.then()
				.log().all()
				.body("text",equalTo ("Playing "+defaultgame+" is fun!"))  
				.statusCode(200)
				.statusLine("HTTP/1.1 200 ")
				.contentType("application/json");
				pojodata dataclass= response.as(pojodata.class); //JSON TO POJO (created data class and used it to store/parse the JSON)
		        assertNotNull(dataclass.getId());
		        assertTrue(dataclass.getId()>0);
		        assertTrue(dataclass.getText().contains(defaultgame));		//verified, the return value is default game=Sudoku by using Junit assertion

	}
	
	@Test
	public void schema_check()
	{
		given().get(Base_Url).then().assertThat().body(JsonSchemaValidator.matchesJsonSchema
        (new File("src/test/java/fun/schema.json"))); // created a JSON schema file and verified the match with the base url.
	}	
	
	@Test
	public void wrongendpoint_case()   //Scenario 3: Edge case scenario: Validating the response when the endpoint is wrong
	{
		String wrong_base_url = "http://localhost:8080/games";
		given().get(wrong_base_url).then().statusCode(404)
		.assertThat().body("error",equalTo("Not Found"))
		.log().all();
		assertNotEquals(wrong_base_url, Base_Url);
				
	}

}
