package com.exception.exhandler.advice;

import com.exception.exception.UserException;
import com.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//@ControllerAdvice(annotations = RestController.class) // 특정 어노테이션이 있는 컨트롤러
//@ControllerAdvice("org.example.controllers") // 특정 패키지(하위 포함) 컨트롤러
//@ControllerAdvice(assignableTypes = {ControllerInterface.class, SpecificController.class}) // 부모 컨트롤러, 특정 컨트롤러
@Slf4j
@RestControllerAdvice // 모든 컨트롤러
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST) // (이게 없으면 상태코드 200)
    @ExceptionHandler(IllegalArgumentException.class) // 해당 Exception이 발생하면 호출
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage()); // 정상처리로 끝남(was까지 가지 않음), api 리턴값 : {"code": "BAD", "message": "잘못된 입력"}
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) { // 처리되지 못한 모든 exception은 다 여기에 걸림(Exception은 부모)
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부오류");
    }

}


