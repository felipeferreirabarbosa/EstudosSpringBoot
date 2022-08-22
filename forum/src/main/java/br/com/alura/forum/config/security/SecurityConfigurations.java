package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.alura.forum.repository.UsuarioRepository;

@EnableWebSecurity
@Configurable
public class SecurityConfigurations extends WebSecurityConfigurerAdapter{

	@Autowired
	private AutenticacaoService autenticacaoService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	//Metodo para configurar autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}

	//Metodo para configurar autorizacao (e.g.: perfil de acesso)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/topicos").permitAll()
		.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
		.antMatchers(HttpMethod.POST, "/auth").permitAll()
		.antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
		.antMatchers(HttpMethod.GET, "/").permitAll()
		.antMatchers("/h2-console/**").permitAll()
		.antMatchers("/swagger-ui/**").permitAll()
		.anyRequest().authenticated()
		.and().csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
		
		  http.csrf().disable();
		  http.headers().frameOptions().disable();
	}
	
	//Metodo para configurar recursos estaticos (e.g.: js, css, img)
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/**.html",
	    		"/v2/api-docs",
	    		"/webjars/**",
	    		"/configuration/**", 
	    		"/swagger-resources/**");
	}
	
	//Metodo Para gerar senha de usuario diretamente no banco para testes
	/*
	 * public static void main(String[] args ) { System.out.println(new
	 * BCryptPasswordEncoder().encode("123456"));
	 * 
	 * }
	 */
}
