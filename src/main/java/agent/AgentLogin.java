package agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class AgentLogin {

    private String login, password, kind;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKind() { return kind; }

    public void setKind(String kind) { this.kind = kind;}

    @SpringBootApplication
    //@EnableMongoRepositories("repository")
    //@ComponentScan({"repository","controller", "agent"})
    public static class Application {

        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }
    }
}
