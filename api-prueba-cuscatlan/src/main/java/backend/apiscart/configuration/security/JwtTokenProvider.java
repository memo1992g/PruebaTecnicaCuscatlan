package backend.apiscart.configuration.security;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

@Component
public class JwtTokenProvider {

	@Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);
        claims.put("created", now);
        claims.put("authorities", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        
        System.out.println("SECRET: "+secretKey);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.encode(secretKey))
                .compact();
    }

    public String wToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(TextCodec.BASE64.encode(secretKey))
                .parseClaimsJws(token)
                .getBody();

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration + 180000); // Agrega 3 minutos (180,000 ms)

        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.encode(secretKey))
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(TextCodec.BASE64.encode(secretKey))
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(TextCodec.BASE64.encode(secretKey)).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
