package com.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {
            if(ex instanceof IllegalArgumentException) {
                log.info("500(IllegalArgumentException) to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage()); // servlet 컨테이너 단계에서 감지하는 에러(500에러가 발생했지만 400 에러 코드를 보내 바꿈) > 400 에러페이지가 나옴
                return new ModelAndView(); // 정상적인 return으로 예외를 무시해버림
            }
        } catch (IOException e) {
            log.error("resolver", e);
        }

        return null; // 다음 ExeptionResolver를 return
    }

}
