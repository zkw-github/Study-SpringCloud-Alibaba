package com.student.zhaokangwei.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * 令牌工具类
 */
public class TokenUtils {

    final static String issuer = "hong";
    final static String secret = "123456";

    static Algorithm algorithm = Algorithm.HMAC256(secret);    //创建一个HMAC256算法对象

    /**
     * 生成令牌
     * @param sign          标识
     * @param issuedTime    令牌创建时间
     * @param expiresTime   令牌过期时间
     * @return
     */
    public static String generate(Object sign, Date issuedTime, Date expiresTime) {
        String token = JWT.create()
                .withIssuer(issuer)             //配置令牌创建者
                .withIssuedAt(issuedTime)       //配置令牌创建时间
                .withExpiresAt(expiresTime)     //配置令牌过期时间
                .withClaim("sign", sign.toString())     //配置令牌携带标识
                .sign(algorithm);               //完成签名，并生成token

        return token;
    }


    /**
     * 验证Token
     * @param token
     * @return 令牌携带标识，如果返回了正常的字符串，说明验证通过。如果返回null，说明验证未通过。
     */
    public static String verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();     //创建JWT验证器对象
            DecodedJWT decodedJWT = verifier.verify(token);
            String result = decodedJWT.getClaim("sign").toString();
            if (result.startsWith("\"")) result = result.substring(1);
            if (result.endsWith("\"")) result = result.substring(0, result.length() - 1);
            return result;
        } catch (Exception e) {
            System.err.println("Token '" + token + "' is not certified\t" + e.getMessage());
            return null;
        }
    }

}
