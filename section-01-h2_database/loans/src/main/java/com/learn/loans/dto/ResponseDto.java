package com.learn.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author vaibhav
 */
@Schema(
        name = "Response",
        description = "Schema to hold successful response information"
)
@Data // Lombok annotation to generate getters, setters, toString, equals, hashCode
@AllArgsConstructor
public class ResponseDto {

	@Schema(
            description = "Status code in the response"
    )
    private String statusCode;

    @Schema(
            description = "Status message in the response"
    )
    private String statusMsg;

}
