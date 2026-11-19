package spec;

import com.epam.http.requests.ServiceInit;
import io.restassured.specification.RequestSpecification;

public final class ApiSpecification {

    private ApiSpecification() {
    }

    public static void init(RequestSpecification requestSpecification) {
        ServiceInit.init(AuthApiSpecification.class, requestSpecification);
        ServiceInit.init(CommentsApiSpecification.class, requestSpecification);
    }
}
