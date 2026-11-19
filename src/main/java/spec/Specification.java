package spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class Specification {

    public static RequestSpecification getBaseSpecification(Map<String, String> config) {
        return new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + config.get("token"))
                .build();
    }

}
