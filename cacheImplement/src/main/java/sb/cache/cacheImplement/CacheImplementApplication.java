package sb.cache.cacheImplement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CacheImplementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CacheImplementApplication.class, args);
	}

}
