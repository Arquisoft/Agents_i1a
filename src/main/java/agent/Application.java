package agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("dbmanagement")
@ComponentScan({"dbmanagement","validator","services","controller", "agent"})
public class Application {

    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }
}