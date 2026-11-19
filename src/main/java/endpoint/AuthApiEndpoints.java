package endpoint;

import com.epam.http.annotations.POST;
import com.epam.http.annotations.ServiceDomain;
import com.epam.http.requests.RestMethod;
import com.epam.http.response.RestResponse;
import dto.auth.AuthDataDto;
import org.apache.commons.lang3.StringUtils;

@ServiceDomain("http://localhost:8081")
public class AuthApiEndpoints {

    @POST("/hub/api/rest/oauth2/interactive/login")
    public static RestMethod login;

    public static RestResponse login(AuthDataDto model) {
        if (model.getUsername() == null || model.getPassword() == null) {
            throw new NullPointerException("Username and password are required");
        }

        var query = new StringBuilder();
        if (StringUtils.isNotBlank(model.getUsername())) {
            query.append("username=").append(model.getUsername());
        }
        if (StringUtils.isNotBlank(model.getPassword())) {
            if (StringUtils.isNotBlank(model.getUsername())) {
                query.append("&");
            }
            query.append("password=").append(model.getPassword());
        }
        return login.queryParams(query.toString()).post(null);
    }
}
