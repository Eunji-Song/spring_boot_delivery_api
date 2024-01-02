package song.deliveryapi.security.jwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    // token에서 특정 정보 꺼내기
    public static String getUserEmail(String token, String secretKey) {
        return Jwts.parserBuilder().setSigningKey(secretKey)
                .build().parseClaimsJws(token)
                .getBody().get("userEmail", String.class);
    }

    // Token 만료시간 확인
    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    // Token 발급
    public static String createToken(String userEmail, String secretKey, Long expiredMs) {
        Claims claims = Jwts.claims();
        claims.put("userEmail", userEmail);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
