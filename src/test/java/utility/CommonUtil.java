package utility;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.ValidationException;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.GameData;

public class CommonUtil {
	
	public static final String BASE_URL = "http://localhost:8080/game";
	public static final String TEMPLATE = "Playing %s is fun!";

	
	public static Response getApiResponse(Map<String, Object> requestParams) {
		RequestSpecification http = RestAssured.given();
		if(requestParams != null) {
			for(Entry<String, Object> entry: requestParams.entrySet()) {
				http.queryParam(entry.getKey(), entry.getValue());
			}
		}
		Response response = http.get(BASE_URL);
		return response;
	}
	
	public static GameData convertStringToGameData(String body) {
		return new Gson().fromJson(body, GameData.class);
	}

	public static ProcessingReport checkJsonSchema(String jsonSchemaPath, String jsonSubject){
		try {
			final JsonNode jsonSchema = JsonLoader.fromPath(jsonSchemaPath);
			final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
			final JsonSchema schema = factory.getJsonSchema(jsonSchema);
			ProcessingReport report = schema.validate(JsonLoader.fromString(jsonSubject));
			System.out.println("Report:" + report);
			return report;
		} catch (ValidationException|ProcessingException|IOException ex) {
			ex.printStackTrace();
			System.out.println("JSON Schema Error Message: " + ex.getMessage());
		}
		return null;
	}
}
