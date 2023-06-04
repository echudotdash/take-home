package fun;

import game_dto.Game_DTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
public class GameTestWithDTO {
    private static final String GAME_NAME = "Playing Chess is fun!";
    private static final String BASE_URL = "http://localhost:8080";
    private static final String VALID_END_POINT = "/game";
    private static final String GAME_NAME_SUDOKU = "Playing Sudoku is fun!";
    @Test
    public void ValidResponseFromGame() {
        Game_DTO gameDto =
                given()
                        .queryParam("name", "Chess")
                        .contentType(ContentType.JSON)
                        .baseUri(BASE_URL)
                        .log().all()
                        .when()
                        .get(VALID_END_POINT)
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .log().all()
                        .extract().as(Game_DTO.class);
        Assert.assertEquals("Game name does not match", GAME_NAME, gameDto.getText());
        Assert.assertNotNull(gameDto.getId());

    }

    @Test
    public void ValidResponseFromGameByDefault() {
        Game_DTO gameDto =
                given()
                        .contentType(ContentType.JSON)
                        .baseUri(BASE_URL)
                        .log().all()
                        .when()
                        .get(VALID_END_POINT)
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .log().all()
                        .extract().as(Game_DTO.class);
        Assert.assertEquals("Game name does not match", GAME_NAME_SUDOKU, gameDto.getText());
        Assert.assertNotNull(gameDto.getId());
    }

}