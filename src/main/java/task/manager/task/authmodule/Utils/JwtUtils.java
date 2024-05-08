package task.manager.task.authmodule.Utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import task.manager.task.authmodule.exceptions.InvalidValidationKeyException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;


@Slf4j
@RequiredArgsConstructor
@Service
public class JwtUtils {

    private final Environment environment;

    public String generateToken(Authentication authentication) {

        User principal = (User) authentication.getPrincipal();

        String expiration = environment.getProperty("token.expiration_time");

        String tokenSecret= environment.getProperty("token.secret");

        return generateJwtTokenString(principal.getUsername(),expiration, tokenSecret);
    }

    public boolean validateToken(String token, String secretKey) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey(secretKey)).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw new InvalidValidationKeyException("Provided invalid token.");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new InvalidValidationKeyException("Provided invalid token.");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw new InvalidValidationKeyException("Provided invalid token.");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw new InvalidValidationKeyException("Provided invalid token.");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw new InvalidValidationKeyException("Provided invalid token.");
        } catch (Exception e){
            throw new InvalidValidationKeyException("Provided invalid token.");
        }
    }


    public String getJwtSubject(String token, String secretKey){
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(getSigningKey(secretKey)).build();
        String subject = "";
        try{
            Jwt<Header, Claims> parsedToken = jwtParser.parse(token);
            subject = parsedToken.getBody().getSubject();
        }catch(Exception e){
            throw new InvalidValidationKeyException("Provided invalid token.");
        }
        return subject;
    }

    public String validateJwtAndGetSubject(String token, String secretKey){
        String subject = "";
        boolean isValid = validateToken(token, secretKey);
        if (isValid)
            subject = getJwtSubject(token, secretKey);
        return subject;
    }

    private String generateJwtTokenString(String subject, String expiration, String secret) {
        Instant now = Instant.now();

        //Generate the validation token
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(Date.from(now.plusMillis(Long.parseLong(expiration))))
                .setIssuedAt(Date.from(now))
                .signWith(getSigningKey(secret))
                .compact();

    }

    private SecretKey getSigningKey(String secretKey) {
        byte[]  secretKeyBytes = Base64.getEncoder().encode(Objects.requireNonNull(secretKey).getBytes());
        return new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
    }


}
