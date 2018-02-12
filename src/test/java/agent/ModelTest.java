package agent;

import static org.junit.Assert.*;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import main.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("repository")
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class ModelTest {
    @Value("${local.server.port}")
    private int port;

    private URL base;
   // private MongoClientURI base;
    private RestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
        //this.base = new MongoClientURI("mongodb://Loader_i1a:EIIASW2018$@ds127888.mlab.com:27888/loader_i1a_db");
        //noinspection deprecation
        template = new TestRestTemplate();
    }

    @Test
    public void testUserInfo() throws Exception {
        String password;
        String name;
        String email;
        String location;
        String kind;
        String NIF;
        name = "name";
        password = "password";
        email = "mail@mail.com";
        location = "caceres";
        NIF = "10203040A";
        kind = "person";
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        AgentInfo user = new AgentInfo(name,email,kind,NIF,password);
        AgentInfo user2 = new AgentInfo(name,email,kind,NIF,password,location);

        assertTrue(user.getPassword().equals(password));
        assertTrue(user.getName().equals(name));
        assertTrue(user.getEmail().equals(email));
        assertTrue(user.getLocation().equals(""));
        assertTrue(user.getKindCode().equals("1"));


        assertTrue(user2.getName().equals(name));
        assertTrue(user2.getEmail().equals(email));
        assertTrue(user2.getLocation().equals(location));


    }

    @Test
    public void testCitizenDTO()throws Exception{
        AgentDTO citizen = new AgentDTO();
        assertNotNull(citizen);
    }

}
