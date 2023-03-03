package fun;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import model.GameApiResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import utils.ReadConfig;
import utils.RestClient;

public class GameTests {
    public static ReadConfig readConfig;
    public static String configFilePath = System.getProperty("user.dir").concat("//src//test//java//config.properties");
    public static String baseUrl;

    public static RestClient restClient;

    @Before
    public void setUp() {
        readConfig = new ReadConfig(configFilePath);
        baseUrl = readConfig.readPropertiesFile("HOST");
        restClient = new RestClient();
    }

    @Test
    public void testGameGetApiStatus() {
        HttpResponse response = restClient.executeGet(baseUrl);
        Assert.assertEquals("Response status code", response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    @SneakyThrows
    @Test
    public void testGameGetApiId() {
        HttpResponse response = restClient.executeGet(baseUrl);
        String jsonString = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        GameApiResponse gameApiResponse = objectMapper.readValue(jsonString, GameApiResponse.class);
        Assert.assertTrue("Game ID", gameApiResponse.getId()>0);
    }

    @SneakyThrows
    @Test
    public void testGameGetApiText() {
        HttpResponse response = restClient.executeGet(baseUrl);
        String jsonString = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        GameApiResponse gameApiResponse = objectMapper.readValue(jsonString, GameApiResponse.class);
        Assert.assertEquals("Game Text", gameApiResponse.getText(), "Playing Sudoku is fun!");
    }

    @SneakyThrows
    @Test
    public void testGameGetApiForChess() {
        HttpResponse response = restClient.executeGet(baseUrl, "Chess");
        String jsonString = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        GameApiResponse gameApiResponse = objectMapper.readValue(jsonString, GameApiResponse.class);
        Assert.assertEquals("Game Text", gameApiResponse.getText(), "Playing Chess is fun!");
    }

    @SneakyThrows
    @Test
    public void testGameGetApiForCricket() {
        HttpResponse response = restClient.executeGet(baseUrl, "Cricket");
        String jsonString = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        GameApiResponse gameApiResponse = objectMapper.readValue(jsonString, GameApiResponse.class);
        Assert.assertEquals("Game Text", gameApiResponse.getText(), "Playing Cricket is fun!");
    }

    @Test
    public void testGameGetApiForChess() {
        HttpResponse response = restClient.executeGet(baseUrl, "Cricket");
        String jsonString = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        GameApiResponse gameApiResponse = objectMapper.readValue(jsonString, GameApiResponse.class);
        Assert.assertEquals("Game Text", gameApiResponse.getText(), "Playing Cricket is fun!");
    }
    
}
