package fun;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import fun.APIUtil;

@Configuration
public class TestConfig {

	@Bean
	APIUtil apiUtil() {
		return new APIUtil();
	}
}