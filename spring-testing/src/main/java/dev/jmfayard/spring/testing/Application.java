package dev.jmfayard.spring.testing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = ContextInstanceDataAutoConfiguration.class)
public class Application {

  private static final Logger LOG = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  CommandLineRunner welcomeMessage() {
    return runner -> {
      String welcomeMessage = """
        \n
        Welcome to the Testing Spring Boot Applications Masterclass!
        If you can see this in the console, you successfully started the course application.
        """;

      LOG.info(welcomeMessage);
    };
  }
}
