package ar.edu.unju.edm.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ar.edu.unju.edm.app.model.Usuario;

@SpringBootApplication
public class PvFebrero2023SotoLeonardoApplication implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(PvFebrero2023SotoLeonardoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String contrasena = "123";
		
		for(int i=0; i<2; i++)
		{
			String bCryptPassword = passwordEncoder.encode(contrasena);
			System.out.println(bCryptPassword);
		}
		
	}

}
