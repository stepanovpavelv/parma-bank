package parma.edu.money_transfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories("parma.edu.money_transfer.dao")
@EntityScan("parma.edu.money_transfer.model")
public class MoneyTransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyTransferApplication.class, args);
	}

}
