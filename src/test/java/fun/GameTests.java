package fun;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class GameTests {

    @Test(dataProviderClass =ReadData.class,dataProvider = "sendData")
    public void getRequest(String name){
        RestAssured.baseURI="http://localhost:8080/game";
        String response= given()
                .queryParam("name",name)
                .log().all()
                .when()
                .get()
                .then()
                .assertThat().statusCode(200)
                .headers("Content-Type","application/json;charset=UTF-8")
                //verify JSON Schema
                .body(JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir")+"\\src\\test\\resources\\schema.json")))
                .log().all()
                .extract().response().asString();

        JsonPath js=new JsonPath(response);
        String text=js.getString("text");
        int id=js.getInt("id");

        //Verify when no query parameter then the game endpoint by default sets game to Sudoku
        //else the game endpont hits the corresponding API

        if(name.equalsIgnoreCase("")){
            Assert.assertEquals(text,"Playing Sudoku is fun!");
        }else
            Assert.assertTrue(text.contains(name));




        //verifying the new id is always greater than the old id.
        RestAssured.baseURI="http://localhost:8080/game";
        String response1= given()
                .queryParam("name",name)
                .log().all()
                .when()
                .get()
                .then()
                .assertThat().statusCode(200).log().all()
                .extract().response().asString();
        JsonPath js1=new JsonPath(response1);
        int id1=js1.getInt("id");


        Assert.assertTrue(id1>id);




    }

    @Test
    public void sendBadRequest(){
        RestAssured.baseURI="http://localhost:8080/game";
        String response= given()
                .queryParam("naem","")
                .log().all()
                .when()
                .get()
                .then()
                .assertThat().statusCode(400)
                .headers("Content-Type","application/json;charset=UTF-8")
                .log().all()
                .extract().response().asString();
    }


}
