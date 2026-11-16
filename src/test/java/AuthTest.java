import base.BaseApiTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class AuthTest extends BaseApiTest {

    protected static Stream<Arguments> incorrectCredentialsDataProvider() {
        return extractTestData(DataFileType.AUTH, "incorrectCredentials");
    }

    protected static Stream<Arguments> correctCredentialsDataProvider() {
        return extractTestData(DataFileType.AUTH, "correctCredentials");
    }

    @ParameterizedTest
    @MethodSource("incorrectCredentialsDataProvider")
    @DisplayName("Неправильные данные для входа. Статус код 400")
    void incorrectCredentials_returns400(Map<String, String> credentials) {
        var username = credentials.keySet().stream().findFirst().orElseThrow();
        var password = credentials.values().stream().findFirst().get();

        var req = given().spec(requestSpecification);
        if (StringUtils.isNotBlank(username)) req = req.queryParam("username", username);
        if (StringUtils.isNotBlank(password)) req = req.queryParam("password", password);

        req.when()
                .post(LOGIN_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @ParameterizedTest
    @MethodSource("correctCredentialsDataProvider")
    @DisplayName("Правильные данные для входа. Статус код 200")
    void correctCredentials_returns200(Map<String, String> credentials) {
        var username = credentials.keySet().stream().findFirst().orElseThrow();
        var password = credentials.values().stream().findFirst().get();

        var req = given().spec(requestSpecification);
        if (StringUtils.isNotBlank(username)) req = req.queryParam("username", username);
        if (StringUtils.isNotBlank(password)) req = req.queryParam("password", password);
        req.when()
                .post(LOGIN_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(200);
    }

    @Test
    @DisplayName("Запрос без авторизации. Статус код 401")
    void withoutAuth_returns401() {
        given().spec(requestSpecification)
                .when()
                .post(ISSUES_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(401);
    }
}
