package fun;

import fun.uitl.ConfigurationReader;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class GameBaseTest {

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = ConfigurationReader.getProperty("base_url");
    }
}
