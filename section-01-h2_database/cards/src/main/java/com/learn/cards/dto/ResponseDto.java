package com.learn.cards.dto;

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
@Data
public class ResponseDto {
	

    public ResponseDto(String statusCode, String statusMsg) {
		super();
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;
	}

	@Schema(
            description = "Status code in the response"
    )
    private String statusCode;

    @Schema(
            description = "Status message in the response"
    )
    private String statusMsg;
    
}
