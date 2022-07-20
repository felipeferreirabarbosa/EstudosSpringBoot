package br.com.alura.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${forum.jwt.expiration}")
	private String expiracao;
	
	@Value("${forum.jwt.secret}")
	private String segredo;

	public String gerarToken(Authentication authentication) {
		Usuario logado = (Usuario) authentication.getPrincipal();
		Date hoje = new Date();
		@SuppressWarnings("deprecation")
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiracao));
		return Jwts.builder()
				.setIssuer("API do Forum da Alura")
				.setSubject(logado.getId().toString())
				.setIssuedAt(hoje)
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, segredo)
				.compact();
			
	}

	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(segredo).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdUsuario(String token) {
		Claims claims = Jwts.parser().setSigningKey(segredo).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

}
