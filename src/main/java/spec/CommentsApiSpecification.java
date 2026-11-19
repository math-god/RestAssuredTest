package spec;

import com.epam.http.annotations.ContentType;
import com.epam.http.annotations.POST;
import com.epam.http.annotations.ServiceDomain;
import com.epam.http.requests.RestMethod;
import com.epam.http.response.RestResponse;
import model.comment.CommentDataDto;


@ServiceDomain("http://localhost:8080")
public class CommentsApiSpecification {

    @POST("/spec/issues/{issueKey}/comments")
    @ContentType(io.restassured.http.ContentType.JSON)
    public static RestMethod createComment;

    public static RestResponse createComment(CommentDataDto comment) {
        return createComment.pathParams(comment.getIssueName()).post(comment);
    }
}
