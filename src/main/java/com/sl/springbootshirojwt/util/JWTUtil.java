package com.sl.springbootshirojwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {


    private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    /**
     * token过期时间（
     */
    private static final long TOKEN_EXPIRE_TIME = 5 * 60 * 1000;

    private static final String SECRET = "OGM4ODc1ZDU4YjJhNzBjNg==";

    /**
     * @param username
     * @param secret
     * @param userId
     * @return JWT生成Token
     * JWT构成: header, payload, signature
     */
    public static String sign(String username, Integer userId) {
        Date date = new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        // 附带username信息
        return JWT.create().withHeader(map) //header
                .withClaim("username", username)
                .withClaim("user_id", userId)
                .withIssuedAt(new Date()) // sign time
                .withExpiresAt(date)  // expire time
                .sign(algorithm);
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     */
    public static Map<String, Object> verifyToken(String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT jwt = verifier.verify(token);
            map.put("exception", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            map.put("exception",e);
            // e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
        }
        return map;
    }

    /**
     * 获取用户ID
     *
     * @param token
     * @return
     */
    public static Integer getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("user_id").asInt();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
       /* String token = JWTUtil.sign("sl", 12);
        System.out.println(getUserId(token));
        System.out.println(getUsername(token));*/
        Map<String,Object> map=verifyToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjo5LCJleHAiOjE1Nzg3Mjg2OTYsImlhdCI6MTU3ODcyODM5NiwidXNlcm5hbWUiOiJ4bSJ9.Y2f-xcW5_TqU_jWV9g6z73kdOFdQmo-K-MzraRVy2wk");
    }

}
