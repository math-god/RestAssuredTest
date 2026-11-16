import base.BaseApiTest;
import io.restassured.http.ContentType;
import model.CommentDataModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class CommentTest extends BaseApiTest {

    private static final String COMMENT_URL_FORMAT = "/api/issues/%s/comments";

    protected static Stream<Arguments> commentExampleDataProvider() {
        return extractTestData(DataFileType.COMMENT, "commentExample");
    }

    protected static Stream<Arguments> emptyCommentDataProvider() {
        return extractTestData(DataFileType.COMMENT, "emptyComment");
    }

    @ParameterizedTest
    @MethodSource("commentExampleDataProvider")
    @DisplayName("Создание комментария. Статус код 200")
    void createComment_return200(String comment) {
        var url = String.format(COMMENT_URL_FORMAT, getIssueName());

        given()
                .body(new CommentDataModel(comment))
                .contentType(ContentType.JSON)
                .spec(requestSpecification)
                .auth().oauth2(getToken())
                .when()
                .post(url)
                .then()
                .log().ifValidationFails()
                .statusCode(200);
    }

    @ParameterizedTest
    @MethodSource("emptyCommentDataProvider")
    @DisplayName("Создание пустого комментария. Статус код 400")
    void createEmptyComment_return400(String comment) {
        var url = String.format(COMMENT_URL_FORMAT, getIssueName());

        given()
                .body(new CommentDataModel(comment))
                .contentType(ContentType.JSON)
                .spec(requestSpecification)
                .auth().oauth2(getToken())
                .when()
                .post(url)
                .then()
                .log().ifValidationFails()
                .statusCode(400);
    }
}
