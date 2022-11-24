package com.un.pingpong.interceptor;

import com.un.pingpong.exception.PingPongException;
import com.un.pingpong.model.UserSession;
import com.un.pingpong.repository.UserSessionRepository;
import com.un.pingpong.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.un.pingpong.constants.Constants.*;

@RequiredArgsConstructor
public class RequestProcessingTimeInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory
            .getLogger(RequestProcessingTimeInterceptor.class);

   final UserSessionRepository userSessionRepository;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        logger.info("Request URL::" + request.getRequestURL().toString()
                + ":: Start Time=" + System.currentTimeMillis());
        request.setAttribute("startTime", startTime);

        if(!request.getMethod().equalsIgnoreCase("OPTIONS") && request.getRequestURI().equalsIgnoreCase(PPMATCH_URL) && (request.getHeader(HttpHeaders.AUTHORIZATION) == null || request.getHeader(HttpHeaders.AUTHORIZATION).isEmpty()))
         throw new PingPongException(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase());
        else if(request.getMethod().equalsIgnoreCase("GET")  && request.getRequestURI().equalsIgnoreCase(PPMATCH_URL))
        {
            Claims token = TokenUtils.parseJWT(request.getHeader(HttpHeaders.AUTHORIZATION));
            UserSession session = userSessionRepository.findAllByTokenAndUserId(request.getHeader(HttpHeaders.AUTHORIZATION), Long.valueOf(token.getId()));
            if(session == null)
                throw new PingPongException(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase());
            request.setAttribute("userId", session.getUserId());

        }else  if(!request.getMethod().equalsIgnoreCase("OPTIONS") && request.getRequestURI().equalsIgnoreCase(HEALTH_URL) && (request.getHeader(HttpHeaders.AUTHORIZATION) == null || request.getHeader(HttpHeaders.AUTHORIZATION).isEmpty()))
            throw new PingPongException(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase());
        else if(request.getMethod().equalsIgnoreCase("GET") & request.getRequestURI().equalsIgnoreCase(HEALTH_URL))
        {
            if(!request.getHeader(HttpHeaders.AUTHORIZATION).equals(HEALTH_PASS))
                throw new PingPongException(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("Request URL::" + request.getRequestURL().toString()
                + " Sent to Handler :: Current Time=" + System.currentTimeMillis());
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        logger.info("Request URL::" + request.getRequestURL().toString()
                + ":: End Time=" + System.currentTimeMillis());
        logger.info("Request URL::" + request.getRequestURL().toString()
                + ":: Time Taken=" + (System.currentTimeMillis() - startTime));
    }

}
