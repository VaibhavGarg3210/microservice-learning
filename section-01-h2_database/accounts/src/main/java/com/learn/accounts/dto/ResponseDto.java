package com.learn.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Schema(
        name = "Response",
        description = "Schema to hold successful response information"
)
public class ResponseDto {

	@Schema(
	        description = "Status code in the response",example = "200"
	)
    private String statusCode;
	@Schema(
	        description = "Status message in the response",example = "Response processed successfully"
	)
    private String statusMsg;
    
}
