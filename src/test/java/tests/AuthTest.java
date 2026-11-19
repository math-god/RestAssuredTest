package tests;

import endpoint.AuthApiEndpoints;
import dto.auth.AuthDataDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.hamcrest.Matchers.*;

import java.util.Map;
import java.util.stream.Stream;

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

        var response = AuthApiEndpoints.login(new AuthDataDto(username, password)).getRaResponse();
        response.then().log().ifValidationFails()
                .statusCode(400)
                .header("Content-Type", is("text/csv")); // Ошибка приходит в формате CSV
    }

    @ParameterizedTest
    @MethodSource("correctCredentialsDataProvider")
    @DisplayName("Правильные данные для входа. Статус код 200")
    void correctCredentials_returns200(Map<String, String> credentials) {
        var username = credentials.keySet().stream().findFirst().orElseThrow();
        var password = credentials.values().stream().findFirst().get();

        var response = AuthApiEndpoints.login(new AuthDataDto(username, password)).getRaResponse();
        response.then().log().ifValidationFails()
                .statusCode(200)
                .header("Content-Type", is("text/html")); // Успешный запрос возвращает html
    }
}
