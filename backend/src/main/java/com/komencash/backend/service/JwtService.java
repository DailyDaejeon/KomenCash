package com.komencash.backend.service;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.komencash.backend.dto.TeacherSelectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

@Component
public class JwtService {

    public static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private String signature = "VUETOKEN";
    private Long expireMin = 5L;

    //	로그인 성공시 사용자 정보를 기반으로 JWTToken을 생성하여 반환.

    public String create(TeacherSelectResponse teacherSelectResponse) {

        JwtBuilder jwtBuilder = Jwts.builder();
//		JWT Token = Header + Payload + Signature

//		Header 설정
        jwtBuilder.setHeaderParam("typ", "JWT"); // 토큰의 타입으로 고정 값.
        Date date  = new Date();
        long t = date.getTime();

        Date expired_token_date = new Date((System.currentTimeMillis() +(1000*60*60*24*14)));

//		Payload 설정
        jwtBuilder
                .setSubject("로그인토큰") // 토큰의 제목 설정
//                .setExpiration(new Date((System.currentTimeMillis() +(1000*60*60*24*14))))
                .setExpiration(expired_token_date)
                .claim("token_expired", expired_token_date)
                .claim("id", teacherSelectResponse.getId())
                .claim("email", teacherSelectResponse.getEmail())
                .claim("phone_number", teacherSelectResponse.getPhoneNumber())
                .claim("nickname", teacherSelectResponse.getNickname());

//		signature 설정
        jwtBuilder.signWith(SignatureAlgorithm.HS256, signature.getBytes());

//		마지막 직렬화 처리
        String jwt = jwtBuilder.compact();
//        logger.info("jwt : {}", jwt);

        return jwt;
    }

    //	전달 받은 토큰이 제대로 생성된것이니 확인 하고 문제가 있다면 RuntimeException을 발생.

    public void checkValid(String jwt) {
//		예외가 발생하지 않으면 OK
        try {
            Jwts.parser().setSigningKey(signature.getBytes()).parseClaimsJws(jwt);
        } catch (SignatureException e) {
            logger.info("Invalid JWT signature");
        }

    }

    //	JWT Token을 분석해서 필요한 정보를 반환.

    public Map<String, Object> get(String jwt) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(signature.getBytes()).parseClaimsJws(jwt);
        } catch (final Exception e) {
            throw new RuntimeException();
        }

        logger.info("claims : {}", claims);
        // Claims는 Map의 구현체이다.
        return claims.getBody();
    }
    public int getIdFromJwt(String jwt) {
        int id = (int) get(jwt).get("id");
        return id;
    }

}

