package com.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class ErrorPageController {

    //RequestDispatcher 상수로 정의되어 있음
    public static final String ERROR_EXCEPTION = "javax.servlet.error.exception";
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
    public static final String ERROR_MESSAGE = "javax.servlet.error.message";
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";
    public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";

    @RequestMapping("/error-page/400")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        printErrorInfo(request,response);
        return "error/404"; // error-page라고 하면 패키지 인식이 안됨(error로만 인식)
    }

    // 스프링 부트는 이러한 기능이 BasicErrorController에 기본적으로 구현되어 있음
    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("PAGE ERROR 500");
        printErrorInfo(request,response);
        return "error/500";
    }

    // 스프링 부트는 이러한 기능이 BasicErrorController에 기본적으로 구현되어 있음
    @RequestMapping(value = "/error-page/500", produces = MediaType.APPLICATION_JSON_VALUE) // 더 자세한 쪽이 우선순위
    public ResponseEntity<Map<String,Object>> errorJson500(HttpServletRequest request, HttpServletResponse response) {

        log.info("API ERROR 500");

        Map<String, Object> result = new HashMap<>();
        Exception ex = (Exception) request.getAttribute(ERROR_EXCEPTION);

        result.put("status", request.getAttribute(ERROR_STATUS_CODE));
        result.put("message", ex.getMessage());

        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));
    }

    private void printErrorInfo(HttpServletRequest request, HttpServletResponse response) {
        log.error("ERROR_EXCEPTION: ex=", request.getAttribute(ERROR_EXCEPTION));
        log.error("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.error("ERROR_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE)); //ex의 경우 NestedServletException 스프링이 한번 감싸서 반환
        log.error("ERROR_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI));
        log.error("ERROR_SERVLET_NAME: {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.error("ERROR_STATUS_CODE: {}", request.getAttribute(ERROR_STATUS_CODE));

        log.error("dispatchType={}", request.getDispatcherType());
    }

}
