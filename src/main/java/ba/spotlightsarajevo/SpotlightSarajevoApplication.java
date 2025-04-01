package ba.spotlightsarajevo;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SpotlightSarajevoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotlightSarajevoApplication.class, args);
	}

	@Bean
	public MapPropertySource envVariables() {
		Dotenv dotenv = Dotenv.load();
		Map<String, Object> envVars = new HashMap<>();

		envVars.put("DB_URL", dotenv.get("DB_URL"));
		envVars.put("DB_USER", dotenv.get("DB_USER"));
		envVars.put("DB_PASS", dotenv.get("DB_PASS"));

		return new MapPropertySource("dotenvProperties", envVars);
	}

}
