package br.com.carteira.infra.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.carteira.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${jjwt.secret}")
	private String secret;

	public String gerarToken(Authentication authentication) {
		Usuario logado = (Usuario) authentication.getPrincipal();
		
		return Jwts
				.builder()
				.setSubject(logado.getId().toString())
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	public boolean isValido(String token) {
		
		try {

			Jwts
			.parser()
			.setSigningKey(secret)
			.parseClaimsJws(token); //não retorna boolean , ele retonar um objeto e se o token estiver invalido ele retorna exception
			
			return true;
			
		} catch (Exception e) {
			return false;
		}
		
	}

	public Long extrairIdUsuario(String token) {
		
		Claims claims = Jwts
							.parser()
							.setSigningKey(secret)
							.parseClaimsJws(token)
							.getBody(); 
			
		return Long.parseLong(claims.getSubject());
	}

}
