package ar.edu.unju.edm.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/", "/index", "/css/**", "/js/**", "/img/**").permitAll()
			.antMatchers("/form/**").hasAnyRole("ADMIN")
			.antMatchers("/eliminar/**").hasAnyRole("ADMIN")		
			.antMatchers("/listarUsuarios").hasAnyRole("ADMIN")
			.anyRequest().authenticated()
			
			.and()
				.formLogin().loginPage("/login").permitAll()
//				.usernameParameter("dni")
//				.passwordParameter("contrasena")
			.and()
				.logout().permitAll();
	}

	@Bean
	public static BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception
	{
		PasswordEncoder encoder = passwordEncoder();
		UserBuilder users = User.builder().passwordEncoder(password -> encoder.encode(password));
		
		builder.inMemoryAuthentication()
			.withUser(users.username("admin").password("123").roles("ADMIN", "USER"))
			.withUser(users.username("leo").password("123").roles("USER"));
	}

}
