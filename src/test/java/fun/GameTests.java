package fun;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import Resources.JsonResponseClass;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GameTests {

	

   @DisplayName("validate 200 status code with different data(query parameter)")
   @ParameterizedTest
   @MethodSource("Resources.DataClass#DataProvider")
  
   public void validateStatusCode(String parameter) {
	RestAssured.baseURI="http://localhost:8080";
	RequestSpecification httpRequest = RestAssured.given();	
	httpRequest.queryParam("name",parameter);
	Response response = httpRequest.request(Method.GET,"/game");
	System.out.println(response.getBody().asString());
	System.out.println(response.getStatusCode());
	assertEquals(response.getStatusCode(), 200);
	
	
	

	
	
   }
   
   @DisplayName("validate game's name present in text property ")
   @ParameterizedTest
   @MethodSource("Resources.DataClass#DataProvider")
   public void validateGamesName(String parameter) {
	RestAssured.baseURI="http://localhost:8080";
	RequestSpecification httpRequest = RestAssured.given();
	httpRequest.queryParam("name",parameter);	
	
	Response response = httpRequest.request(Method.GET,"/game");
	System.out.println(response.jsonPath().getString("text"));
	assertTrue(response.jsonPath().getString("text").contains(parameter));
	

	
   }
   @DisplayName("validate Json Schema")
   @Test
   public void validateJsonSchema() {
	   
	   File file = new File("src\\test\\java\\Resources\\JsonSchema.json");
	   String path = file.getAbsolutePath();
	   System.out.println(path);
			RestAssured
			.given()
			.baseUri("http://localhost:8080/")
			.basePath("game?name=")
			.get()
			.then()
			.body(JsonSchemaValidator.matchesJsonSchema(new File(path)));
			   
   }
   
   @DisplayName("validate all properties in Json Response")
   @Test
   public void validateProperties() {
	   
	   RestAssured.baseURI="http://localhost:8080";
		RequestSpecification httpRequest = RestAssured.given();	
		
		Response response = httpRequest.request(Method.GET,"/game");
		assertTrue(response.body().asString().contains("id"));
		assertTrue(response.body().asString().contains("text"));		   
   }
   
   
   
   @DisplayName("validate Json response stored in Java data class")
   @Test
   public void validateResponseSotedInClass() {
	   
	   RestAssured.baseURI="http://localhost:8080";
		RequestSpecification httpRequest = RestAssured.given();	
		
		Response response = httpRequest.request(Method.GET,"/game");
		JsonResponseClass jsonResponseClass = response.getBody().as(JsonResponseClass.class);
		
		assertEquals(jsonResponseClass.getText(),"Playing Sudoku is fun!");	
		
		
   }
  
   
   @DisplayName("validate 404 status code with invalid URL")
   @Test
   public void validateStatusCode_InvalidURL() {
	   
	   RestAssured.baseURI="http://localhost:8080";
		RequestSpecification httpRequest = RestAssured.given();	
		
		Response response = httpRequest.request(Method.GET,"/gamesss");
		assertEquals(response.getStatusCode(), 404);	
		
   }	
		@DisplayName("validate default responses without any query parameter"+ "")
		   @Test
		   public void validateDefaultResponse() {
			   
			   RestAssured.baseURI="http://localhost:8080";
				RequestSpecification httpRequest = RestAssured.given();	
				
				Response response = httpRequest.request(Method.GET,"/gamesss");
				assertEquals(response.getStatusCode(), 404);	

   

}
}
