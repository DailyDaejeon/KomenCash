package com.komencash.backend.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class JwtInterceptor implements HandlerInterceptor {

    public static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.info(request.getMethod() + " : " + request.getServletPath());
        if(request.getMethod().equals("OPTIONS")) {

            return true;
        }
        // request의 parameter에서 auth_token으로 넘어온 녀석을 찾아본다.
        // String token = request.getParameter("auth_token");
        String token = request.getHeader("auth-token");

        // 유효한 토큰이면 진행, 그렇지 않으면 예외를 발생시킨다.
        if (token!=null && token.length()>0) {
            Jwts.parser().setSigningKey("VUETOKEN".getBytes()).parseClaimsJws(token);
            return true;
        }
        else {
            System.out.println("토큰없습니다.");
//            throw new RuntimeException("인증 토큰이 없습니다.");
            return true;
        }
    }


}
