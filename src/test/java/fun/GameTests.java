package fun;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.github.fge.jsonschema.core.report.ProcessingReport;

import io.restassured.response.Response;
import junit.framework.Assert;
import model.GameData;
import utility.CommonUtil;

public class GameTests {
	
	private static Set<Integer> idSet = new HashSet<Integer>();

	
	@RunWith(Parameterized.class)
	public static class ParameterizedGameTest {
		private String gameName;

		
		public ParameterizedGameTest(String gameName) {
			this.gameName = gameName;
		}
		
		@Parameterized.Parameters
		   public static Collection gameNames() {
		      return Arrays.asList(new Object[][] {
		         { "Ludo" },
		         { "Cricket" },
		         { "Candy Crush" },
		         { "Fun run" }	
		         });
		   }


		@Test
		public void validateWithNameValueInRequest() throws IOException {
			HashMap<String, Object> requestParams = new HashMap<String, Object>();
			requestParams.put("name", gameName);
			Response response = CommonUtil.getApiResponse(requestParams);
			
			String body = response.getBody().asString();
			int statusCode = response.getStatusCode();
			GameData gameData = CommonUtil.convertStringToGameData(body);
			System.out.println(body);
			idSet.add(gameData.getId());
			System.out.println("status: " + statusCode);
			Assert.assertEquals(200, response.getStatusCode());
			Assert.assertEquals(String.format(CommonUtil.TEMPLATE, gameName), gameData.getText());
		}
	}
	
	public static class NonParameterizedGameTest {

		@Test
		public void validateSudokuWithEmptyNameValueInRequest() throws IOException {
			HashMap<String, Object> requestParams = new HashMap<String, Object>();
			requestParams.put("name", "");
			Response response = CommonUtil.getApiResponse(requestParams);
			String body = response.getBody().asString();
			int statusCode = response.getStatusCode();
			GameData gameData = CommonUtil.convertStringToGameData(body);
			idSet.add(gameData.getId());
			System.out.println(body);
			System.out.println("status: " + statusCode);
			Assert.assertEquals(200, response.getStatusCode());
			Assert.assertEquals(String.format(CommonUtil.TEMPLATE, "Sudoku"), gameData.getText());
		}

		@Test
		public void validateSudokuWithoutNameKeyInRequest() throws IOException {
			Response response = CommonUtil.getApiResponse(null);
			String body = response.getBody().asString();
			int statusCode = response.getStatusCode();
			GameData gameData = CommonUtil.convertStringToGameData(body);
			idSet.add(gameData.getId());
			System.out.println(body);
			System.out.println("status: " + statusCode);
			Assert.assertEquals(200, response.getStatusCode());
			Assert.assertEquals(String.format(CommonUtil.TEMPLATE, "Sudoku"), gameData.getText());
		}

		@Test
		public void validateSchema() throws IOException {
			String gameSchemaPath = "/Users/sagarliki/git/take-home/src/test/resources/schema/game_schema.json";
			String gameName = "Chess";

			HashMap<String, Object> requestParams = new HashMap<String, Object>();
			requestParams.put("name", gameName);
			Response response = CommonUtil.getApiResponse(requestParams);
			
			String body = response.getBody().asString();
			int statusCode = response.getStatusCode();
			System.out.println(body);
			System.out.println("status: " + statusCode);
			Assert.assertEquals(200, response.getStatusCode());
			ProcessingReport report = CommonUtil.checkJsonSchema(gameSchemaPath, body);
			Assert.assertTrue(report.isSuccess());
			
		}

		@Test
		public void validateUniqueID() {
			Response response = CommonUtil.getApiResponse(null);
			String body = response.getBody().asString();
			GameData gameData = CommonUtil.convertStringToGameData(body);
			Assert.assertFalse(idSet.contains(gameData.getId()));
		}


		@Test
		public void validateIntegerGameName() throws IOException {
			int gameName = 1234;
			HashMap<String, Object> requestParams = new HashMap<String, Object>();
			requestParams.put("name", gameName);
			Response response = CommonUtil.getApiResponse(requestParams);
			String body = response.getBody().asString();
			int statusCode = response.getStatusCode();
			System.out.println(body);
			System.out.println("status: " + statusCode);
			Assert.assertEquals(200, response.getStatusCode());
	}

	
	}
}
