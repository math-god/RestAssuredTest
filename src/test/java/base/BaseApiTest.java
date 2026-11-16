package base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.provider.Arguments;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;


public class BaseApiTest {

    protected static final String LOGIN_URL = "/hub/api/rest/oauth2/interactive/login";
    protected static final String ISSUES_URL = "/api/users/me/recent/issues";

    protected static RequestSpecification requestSpecification;
    protected static final ObjectMapper mapper = new ObjectMapper();
    private static String TOKEN;
    private static String ISSUE_NAME;

    @BeforeAll
    static void setup() {
        var baseUrl = "http://localhost:8080";
        requestSpecification = new RequestSpecBuilder().setBaseUri(baseUrl).build();
    }

    protected static Stream<Arguments> extractTestData(DataFileType type, String key) {
        try {
            switch (type) {
                case AUTH -> {
                    Map<String, Map<String, String>> data = mapper.readValue(type.file, new TypeReference<>() {
                    });
                    return data.get(key)
                            .entrySet()
                            .stream()
                            .map(m -> Arguments.of(
                                    Map.of(m.getKey(), m.getValue()))
                            );
                }
                case COMMENT -> {
                    Map<String, String> data = mapper.readValue(type.file, new TypeReference<>() {
                    });
                    return Stream.of(Arguments.of(data.get(key)));
                }
                default -> {
                    System.err.println("Неизвестный тип файла DataFileType");
                    return Stream.empty();
                }
            }

        } catch (IOException e) {
            System.err.println("Невозможно прочитать файл с данными для тестов: " + e.getMessage());
            return Stream.empty();
        }
    }

    protected String getToken() {
        if (TOKEN == null) {
            try {
                Map<String, String> data = mapper.readValue(DataFileType.CONFIG.file, new TypeReference<>() {
                });
                TOKEN = data.get("token");
            } catch (IOException e) {
                System.err.println("Невозможно прочитать файл с данными для тестов: " + e.getMessage());
            }
        }

        return TOKEN;
    }

    protected String getIssueName() {
        if (ISSUE_NAME == null) {
            try {
                Map<String, String> data = mapper.readValue(DataFileType.COMMENT.file, new TypeReference<>() {
                });
                ISSUE_NAME = data.get("issueName");
            } catch (IOException e) {
                System.err.println("Невозможно прочитать файл с данными для тестов: " + e.getMessage());
            }
        }

        return ISSUE_NAME;
    }

    @AllArgsConstructor
    public enum DataFileType {
        AUTH(new File("src/test/resources/data/auth_data.json")),
        COMMENT(new File("src/test/resources/data/comment_data.json")),
        CONFIG(new File("src/test/resources/data/config.json"));;

        private final File file;
    }
}
