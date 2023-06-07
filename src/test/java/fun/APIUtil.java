package fun;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

@Component
public class APIUtil implements IAPIUtil{
	
	private final static String baseURI = "http://localhost:8080/game";
	
	public APIUtil() {
		super();
	}
	
	public ResponseBody getGameData(String param, String paramValue) {
		RestAssured.baseURI = baseURI;
		RequestSpecification spec = RestAssured.given();
		
		if((param !=null && paramValue != null) && (!param.isEmpty() && !paramValue.isEmpty())) {
			spec.queryParam(param, paramValue);
		}
		
		ResponseBody response = spec.request(Method.GET).getBody();
		
		System.out.println("Response : " + response.asPrettyString());
		
		return response;
	}
	
	public GameObject deserializeGameObject(ResponseBody body) {
		GameObject gm=null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			gm = objectMapper.readValue(body.asString(),GameObject.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gm;
	}

}

