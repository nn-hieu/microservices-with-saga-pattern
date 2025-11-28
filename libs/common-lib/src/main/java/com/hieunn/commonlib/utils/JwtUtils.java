package com.hieunn.commonlib.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private final int expirationInSeconds;
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JwtUtils(final String secretKey, final int expirationInSeconds) {
        this.expirationInSeconds = expirationInSeconds;
        this.algorithm = Algorithm.HMAC256(secretKey);
        this.verifier = JWT.require(algorithm).build();
    }

    public String generateToken(final String subject, final Map<String, Object> claims) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expirationInSeconds);

        JWTCreator.Builder builder = JWT.create()
                .withSubject(subject)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(exp));

        if (claims != null) {
            claims.forEach((k, v) -> this.addClaim(builder, k, v));
        }

        return builder.sign(algorithm);
    }

    public DecodedJWT verifyToken(String token) throws JWTVerificationException {
        return verifier.verify(token);
    }

    public String getStringClaim(String token, String claim) {
        return verifyToken(token).getClaim(claim).asString();
    }

    private void addClaim(JWTCreator.Builder builder, String key, Object value) {
        switch (value) {
            case String v -> builder.withClaim(key, v);
            case Integer v -> builder.withClaim(key, v);
            case Long v -> builder.withClaim(key, v);
            case Boolean v -> builder.withClaim(key, v);
            case Double v -> builder.withClaim(key, v);
            case Date v -> builder.withClaim(key, v);
            default -> builder.withClaim(key, value.toString());
        }
    }
}
