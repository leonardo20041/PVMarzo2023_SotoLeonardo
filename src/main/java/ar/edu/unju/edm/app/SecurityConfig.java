package ar.edu.unju.edm.app;

import javax.sql.DataSource;

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
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/", "/index", "/css/**", "/js/**", "/img/**").permitAll()
			.antMatchers("/form/**").hasAnyRole("ADMIN")
			.antMatchers("/eliminar/**").hasAnyRole("ADMIN")		
			.antMatchers("/listarUsuarios").hasAnyRole("ADMIN")
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
		build.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(encoder)
		.usersByUsernameQuery("select dni, contrasena, enabled from users where dni=?")
		.authoritiesByUsernameQuery("select u.dni, r.rol from roles r inner join users u on (r.user_dni=u.dni) where u.dni=?");
//		UserBuilder users = User.build().passwordEncoder(password -> encoder.encode(password));
//		
//		build.inMemoryAuthentication()
//			.withUser(users.username("admin").password("123").roles("ADMIN", "HUESPED"))
//			.withUser(users.username("soto").password("123").roles("HUESPED"));
	}

}
