package br.com.alura.forum.controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {

		String email;
		String senha;
		
		public String getEmail() {
			return email;
		}
		public String getSenha() {
			return senha;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public void setSenha(String senha) {
			this.senha = senha;
		}
		public UsernamePasswordAuthenticationToken criarAuthenticationToken() {
			return new UsernamePasswordAuthenticationToken(email, senha);
		}
		
		
}
