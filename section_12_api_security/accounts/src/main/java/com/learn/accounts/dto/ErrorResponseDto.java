package com.learn.accounts.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema(
		name = "ErrorResponse",
        description = "Schema to hold error response information"
)
public class ErrorResponseDto {
	
	@Schema(
	        description = "Api Client invoke by client"
	)
    private  String apiPath;

	@Schema(
	        description = "Error code representing the error happened"
	)
    private HttpStatus errorCode;

	@Schema(
	        description = "Error code representing the error message"
	)
    private  String errorMessage;

	@Schema(
	        description = "Error code representing the time when error happen"
	)
    private LocalDateTime errorTime;

    
    

}
