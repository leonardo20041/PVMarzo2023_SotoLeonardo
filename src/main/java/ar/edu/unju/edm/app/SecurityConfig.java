package ar.edu.unju.edm.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ar.edu.unju.edm.app.service.JpaUserDetailsService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/", "/index", "/css/**", "/js/**", "/img/**").permitAll()
			.antMatchers("/form/**").hasAnyRole("ADMIN")
			.antMatchers("/eliminar/**").hasAnyRole("ADMIN")		
			.antMatchers("/listarUsuarios").hasAnyRole("ADMIN")
			.antMatchers("/listarHabitaciones").hasAnyRole("ADMIN", "HUESPED")
			.antMatchers("/verDetalles/**").hasAnyRole("ADMIN", "HUESPED")
			.anyRequest().authenticated()
			
			.and()
				.formLogin().loginPage("/login").permitAll()
				.usernameParameter("dni")
				.passwordParameter("contrasena")
			.and()
				.logout().permitAll()
			.and()
				.exceptionHandling().accessDeniedPage("/error_403");
	}

	@Bean
	public static BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception
	{
		PasswordEncoder encoder = passwordEncoder();
		build.userDetailsService(userDetailsService)		
		.passwordEncoder(encoder);
	}

}
