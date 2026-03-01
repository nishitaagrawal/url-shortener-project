package com.example.urlshortener.interceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.UUID;

@Component
public class URLShortenerInterceptor implements HandlerInterceptor {

    private static final String LOG_REF_ID = "logRefId";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String logRefId = UUID.randomUUID().toString();
        MDC.put(LOG_REF_ID, logRefId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        MDC.clear();
    }
}
