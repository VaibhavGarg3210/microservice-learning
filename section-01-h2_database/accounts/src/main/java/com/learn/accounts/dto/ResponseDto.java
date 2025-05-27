package com.learn.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseDto {

    private String statusCode;

    private String statusMsg;
    
}
