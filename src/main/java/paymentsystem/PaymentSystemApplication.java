package paymentsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
public class PaymentSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(PaymentSystemApplication.class, args);
	}
}
/*
To Do List:
1. add new field (created at)
2. change LocalDate to LocalDateTime
3. return types of methods
4. method names
5. path variable id
6. null pointer exception
7. builder method to mapper
8. i18
9. docker
10. jwt
 */