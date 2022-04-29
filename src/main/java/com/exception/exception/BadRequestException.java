package com.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad") // 에러를 원하는 status로 변경(500 > 400)
public class BadRequestException extends RuntimeException{
}
