package br.com.redesocial;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Startup {

	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
	}

    @Bean
    public com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module hibernate6Module() {
        return new com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module();
    }

}