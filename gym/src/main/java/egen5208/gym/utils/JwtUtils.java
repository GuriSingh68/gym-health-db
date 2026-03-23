package egen5208.gym.utils;

import java.util.Date;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import egen5208.gym.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

@Component
public class JwtUtils {
    private final String secretKey = "thisIsAVerySecureSecretKeyForJwtWhichIsAtLeast32BytesLong!!";
    private final int validity = 86400000; // 24 hours
    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

    public String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("role", user.getRole());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 3. Extract Roles from Token
    public List<SimpleGrantedAuthority> getAuthoritiesFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();

        String role = claims.get("role", String.class);
        if (role == null || role.trim().isEmpty()) {
            return java.util.Collections.emptyList();
        }
        return java.util.Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parse(authToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
