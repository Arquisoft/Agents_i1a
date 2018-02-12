package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableMongoRepositories("repository")
@ComponentScan({"repository","controller", "agent"})
public class Application {

    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }
}