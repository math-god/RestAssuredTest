package api;

import com.epam.http.requests.ServiceInit;
import io.restassured.specification.RequestSpecification;

public final class ApiEndpoints {

    private ApiEndpoints() {
    }

    public static void init(RequestSpecification requestSpecification) {
        ServiceInit.init(AuthApi.class, requestSpecification);
        ServiceInit.init(CommentsApi.class, requestSpecification);
    }
}
