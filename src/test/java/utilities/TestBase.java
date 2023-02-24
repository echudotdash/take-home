package utilities;


import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.port;

public class TestBase {

    @BeforeAll
    public static void connectionSetUp() throws Exception {
        baseURI = "http://localhost";
        port = 8080;
    }
}
