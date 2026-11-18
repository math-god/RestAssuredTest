package tests;

import model.comment.CommentResponseModel;
import api.CommentsApi;
import model.comment.CommentDataModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class CommentTest extends BaseApiTest {

    protected static Stream<Arguments> commentExampleDataProvider() {
        return extractTestData(DataFileType.COMMENT, "commentExample");
    }

    protected static Stream<Arguments> emptyCommentDataProvider() {
        return extractTestData(DataFileType.COMMENT, "emptyComment");
    }

    @ParameterizedTest
    @MethodSource("commentExampleDataProvider")
    @DisplayName("Создание комментария. Статус код 200")
    void createComment_return200(String comment) {
        var response = CommentsApi.createComment(new CommentDataModel(getIssueName(), comment)).getRaResponse();
        var responseModel = response.then().statusCode(200).extract().as(CommentResponseModel.class);
        print(responseModel);
    }

    @ParameterizedTest
    @MethodSource("emptyCommentDataProvider")
    @DisplayName("Создание пустого комментария. Статус код 400")
    void createEmptyComment_return400(String comment) {
        var response = CommentsApi.createComment(new CommentDataModel(getIssueName(), comment)).getRaResponse();
        var responseModel = response.then().statusCode(400).extract().as(CommentResponseModel.class);
        print(responseModel);
    }

    private void print(CommentResponseModel responseModel) {
        var retVal = new StringBuilder();
        retVal.append("Comment id: ").append(responseModel.getId()).append("\nComment type: ").append(responseModel.getType());
        if (responseModel.getError() != null) {
            retVal.append("\nComment error: ").append(responseModel.getError()).append("\nComment error description: ").append(responseModel.getErrorDescription());
        }
        System.out.println(retVal);
    }
}
