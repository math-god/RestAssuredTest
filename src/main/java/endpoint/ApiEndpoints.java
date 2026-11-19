package endpoint;

import com.epam.http.requests.ServiceInit;
import io.restassured.specification.RequestSpecification;

public final class ApiEndpoints {

    private ApiEndpoints() {
    }

    public static void init(RequestSpecification requestSpecification) {
        ServiceInit.init(AuthApiEndpoints.class, requestSpecification);
        ServiceInit.init(CommentsApiEndpoints.class, requestSpecification);
    }
}
