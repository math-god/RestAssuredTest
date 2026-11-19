package tests;

import endpoint.ApiEndpoints;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.provider.Arguments;
import spec.EndpointsSpecification;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;


public class BaseApiTest {

    protected static final ObjectMapper mapper = new ObjectMapper();
    private static String ISSUE_NAME;

    public static final Map<String, String> CONFIG;

    static {
        try {
            Map<String, String> configMap = mapper.readValue(DataFileType.CONFIG.file, new TypeReference<>() {
            });
            CONFIG = Collections.unmodifiableMap(configMap);
        } catch (IOException e) {
            System.err.println("Невозможно прочитать файл с данными для тестов: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    @BeforeAll
    static void setup() {
        ApiEndpoints.init(EndpointsSpecification.getBaseSpecification(CONFIG));
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
