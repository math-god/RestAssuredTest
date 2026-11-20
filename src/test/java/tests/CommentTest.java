package tests;

import dto.comment.CommentResponseDto;
import endpoint.CommentsApiEndpoints;
import dto.comment.CommentDataDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        var response = CommentsApiEndpoints.createComment(new CommentDataDto(getIssueName(), comment)).getRaResponse();
        var responseModel = response.then().extract().as(CommentResponseDto.class);
        assertAll(
                () -> assertEquals(200, response.getStatusCode()),
                () -> assertNotEquals(null, responseModel.getId())
        );

        print(responseModel);
    }

    @ParameterizedTest
    @MethodSource("emptyCommentDataProvider")
    @DisplayName("Создание пустого комментария. Статус код 400")
    void createEmptyComment_return400(String comment) {
        var response = CommentsApiEndpoints.createComment(new CommentDataDto(getIssueName(), comment)).getRaResponse();
        var responseModel = response.then().extract().as(CommentResponseDto.class);
        assertAll(
                () -> assertEquals(400, response.getStatusCode()),
                () -> assertNotEquals(null, responseModel.getError()),
                () -> assertNotEquals(null, responseModel.getErrorDescription())
        );

        print(responseModel);
    }

    private void print(CommentResponseDto responseModel) {
        var retVal = new StringBuilder();
        retVal.append("Comment id: ").append(responseModel.getId()).append("\nComment type: ").append(responseModel.getType());
        if (responseModel.getError() != null) {
            retVal.append("\nComment error: ").append(responseModel.getError()).append("\nComment error description: ").append(responseModel.getErrorDescription());
        }
        System.out.println(retVal);
    }
}
