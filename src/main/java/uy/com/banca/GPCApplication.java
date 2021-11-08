package uy.com.banca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class GPCApplication {

  public static void main(String[] args) {
    SpringApplication.run(GPCApplication.class, args);
  }

}
