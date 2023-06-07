package fun;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.response.ResponseBody;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.MatcherAssert;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class GameTests {
	
	@Autowired
	APIUtil apiUtil;
	
	@Test
	public void verifyDefaultGameData2() {
		ResponseBody response = apiUtil.getGameData(null, null);
		GameObject gm = apiUtil.deserializeGameObject(response);
		
		validateGameObjectSchema(response,"GameSchema.json");
		Assertions.assertEquals("Playing Sudoku is fun!", gm.getText());
	}
	
	@Test
	public void verifyGameDataWithQueryParamGame() {
		ResponseBody response = apiUtil.getGameData("name", "Chess");
		GameObject gm = apiUtil.deserializeGameObject(response);
		
		validateGameObjectSchema(response,"GameSchema.json");
		Assertions.assertEquals("Playing Chess is fun!", gm.getText());

	}
	
	@Test
	public void verifyGameDataWithQueryParamOtherThanChess() {
		ResponseBody response = apiUtil.getGameData("name", "Chess1");
		GameObject gm = apiUtil.deserializeGameObject(response);
		
		validateGameObjectSchema(response,"GameSchema.json");
		Assertions.assertEquals("Playing Chess1 is fun!", gm.getText());
	}
	
	@Test
	public void verifyCounterIncrease() {
		ResponseBody response1 = apiUtil.getGameData("name", "Chess1");
		ResponseBody response2 = apiUtil.getGameData("name", "Chess1");
		
		validateGameObjectSchema(response1,"GameSchema.json");

		GameObject gm1 = apiUtil.deserializeGameObject(response1);
		GameObject gm2 = apiUtil.deserializeGameObject(response2);

		Assertions.assertTrue(gm2.getId()>gm1.getId());
	}
	
	@Test
	public void negativeTestWrongSchema() {
		ResponseBody response = apiUtil.getGameData("name", "Chess1");
		validateGameObjectSchema(response,"GameSchemaWrong.json");

	}
	
	
	private void validateGameObjectSchema(ResponseBody body, String schemaName) {
		MatcherAssert.assertThat(body.asString(), JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaName));
	}
}
