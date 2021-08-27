package com.rrm14.cliente.escola.matrizcurricular.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rrm14.cliente.escola.matrizcurricular.constants.Constantes;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {
	
	@Autowired
	public void configuracaoGlobal(AuthenticationManagerBuilder auth) throws Exception{
		PasswordEncoder pass = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication().withUser(Constantes.USUARIO_AUTENTICACAO)
									 .password(pass.encode(Constantes.SENHA_AUTENTICACAO))
									 .roles(Constantes.PAPEL_USUARIO);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeRequests()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}
}
