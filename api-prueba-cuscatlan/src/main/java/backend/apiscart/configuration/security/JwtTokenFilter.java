package backend.apiscart.configuration.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.TextCodec;

@Component
public class JwtTokenFilter extends OncePerRequestFilter{
	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	@Value("${jwt.secret}")
    private String secretKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			if (existeJWTToken(request, response)) {
				Claims claims = validateToken(request);
				if (claims.get("authorities") != null) {
					setUpSpringAuthentication(claims);
				} else {
					SecurityContextHolder.clearContext();
				}
			} else {
					SecurityContextHolder.clearContext();
			}
			chain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
	}	

	private Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER);
		System.out.println("TOKENNN "+jwtToken);
	    if (jwtToken != null && jwtToken.startsWith(PREFIX)) {
	        jwtToken = jwtToken.replace(PREFIX, "");
	        System.out.println("TOKENNN 2 "+jwtToken);
	        try {
	        	Claims claims = Jwts.parser()
	        	        .setSigningKey(TextCodec.BASE64.encode(secretKey))
	        	        .parseClaimsJws(jwtToken)
	        	        .getBody();

	            System.out.println("Token válido: " + claims);
	            return claims;
	        } catch (JwtException | IllegalArgumentException e) {
	            // Manejar excepciones aquí, por ejemplo, token inválido o expirado.
	            System.out.println("Error al validar el token JWT: " + e.getMessage());
	            e.printStackTrace();
	            
	        }
	    }

	    // Manejar el caso en el que no se proporcionó un token o el formato del encabezado es incorrecto.
	    System.out.println("Token JWT no válido");
	    return null;
	}

	/**
	 * Metodo para autenticarnos dentro del flujo de Spring
	 * 
	 * @param claims
	 */
	private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List) claims.get("authorities");

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);

	}

	private boolean existeJWTToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER);
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
			return false;
		return true;
	}

}
