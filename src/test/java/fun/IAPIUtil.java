package fun;

import org.springframework.stereotype.Component;

import io.restassured.response.ResponseBody;


public interface IAPIUtil {
	public ResponseBody getGameData(String param, String paramValue);
	public GameObject deserializeGameObject(ResponseBody body);

}
