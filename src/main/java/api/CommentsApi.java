package api;

import com.epam.http.annotations.ContentType;
import com.epam.http.annotations.POST;
import com.epam.http.annotations.ServiceDomain;
import com.epam.http.requests.RestMethod;
import com.epam.http.response.RestResponse;
import model.comment.CommentDataModel;


@ServiceDomain("http://localhost:8080")
public class CommentsApi {

    @POST("/api/issues/{issueKey}/comments")
    @ContentType(io.restassured.http.ContentType.JSON)
    public static RestMethod createComment;

    public static RestResponse createComment(CommentDataModel comment) {
        return createComment.pathParams(comment.getIssueName()).post(comment);
    }
}
