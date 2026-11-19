package dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private String id;
    @JsonProperty("$type")
    private String type;
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
}
