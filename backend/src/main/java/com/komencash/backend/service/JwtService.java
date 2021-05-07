package com.komencash.backend.service;

import java.util.Date;
import java.util.Map;

import com.komencash.backend.dto.teacher.TeacherFindResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

    public static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private String signature = "VUETOKEN";
    private Long expireMin = 5L;

    //	로그인 성공시 사용자 정보를 기반으로 JWTToken을 생성하여 반환.
    public String create(TeacherFindResponseDto teacher) {

//		JWT Token = Header + Payload + Signature
        JwtBuilder jwtBuilder = Jwts.builder();

//		Header 설정
        jwtBuilder.setHeaderParam("typ", "JWT"); // 토큰의 타입으로 고정 값.

        Date expired_token_date = new Date(System.currentTimeMillis() * expireMin * 1000 * 60);

//		Payload 설정
        jwtBuilder
                .setSubject("로그인 토큰") // 토큰의 제목 설정
                .setExpiration(new Date((System.currentTimeMillis() +(1000*60*60*24*14))))
                .setExpiration(expired_token_date)
                .claim("token_expired", expired_token_date)
                .claim("id", teacher.getId())
                .claim("email", teacher.getEmail())
                .claim("nickname", teacher.getNickname())
                .claim("phoneNumber", teacher.getPhoneNumber());

//		signature 설정
        jwtBuilder.signWith(SignatureAlgorithm.HS256, signature.getBytes());

//		마지막 직렬화 처리
        String jwt = jwtBuilder.compact();
//        logger.info("jwt : {}", jwt);

        return jwt;
    }


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

