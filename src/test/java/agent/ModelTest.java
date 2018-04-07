package agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URL;

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
// import org.springframework.web.client.RestTemplate;
// import org.springframework.web.client.RestTemplate;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("dbmanagement")
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class ModelTest {
    @Value("${local.server.port}")
    private int port;
    // private URL base;
    // private MongoClientURI base;
    // private RestTemplate template;

    @Before
    public void setUp() throws Exception {
        new URL("http://localhost:" + port + "/");
        //this.base = new MongoClientURI("mongodb://Loader_i1a:EIIASW2018$@ds127888.mlab.com:27888/loader_i1a_db");
        //noinspection deprecation
        new TestRestTemplate();
    }

    @Test
    public void testUserInfo() throws Exception {
        String password;
        String name;
        String email;
        String location;
        String NIF;
        int kind;

        name = "name";
        password = "password";
        email = "mail@mail.com";
        location = "caceres";
        NIF = "10203040A";
        kind = 1;

        AgentInfo user = new AgentInfo(name,email,password,NIF,kind);
        AgentInfo user2 = new AgentInfo(name,email,password,location,NIF,kind);

        assertTrue(user.getPassword().equals(password));
        assertTrue(user.getName().equals(name));
        assertTrue(user.getId().equals(NIF));
        assertTrue(user.getEmail().equals(email));
        assertTrue(user.getLocation().equals(""));
        assertTrue(user.getIdautogenerado() == null);
        assertTrue(user.getKind() == 1);

        assertTrue(user2.getName().equals(name));
        assertTrue(user2.getId().equals(NIF));
        assertTrue(user2.getEmail().equals(email));
        assertTrue(user2.getLocation().equals(location));
        assertTrue(user2.getIdautogenerado() == null);

        assertEquals("AgentInfo{idautogenerado='null', name='name'" +
                ", email='mail@mail.com', password='password', location='', id='10203040A'" +
                ", kind=1}", user.toString());

        assertEquals("AgentInfo{idautogenerado='null', name='name'" +
                ", email='mail@mail.com', password='password', location='caceres', id='10203040A'" +
                ", kind=1}", user2.toString());

        assertFalse(user.hashCode() == user2.hashCode());
        assertFalse(user.equals(user2));
    }

    @Test
    public void testCitizenDTO()throws Exception{
        AgentDTO citizen = new AgentDTO();
        assertNotNull(citizen);

        AgentInfo user = new AgentInfo("Pepi","pepi@mail.com","paasRO", "Pajares","85",1);
        citizen = new AgentDTO(user);
        assertNotNull(citizen);

        assertEquals("Pepi", citizen.getName());
        assertEquals("pepi@mail.com", citizen.getEmail());
        assertEquals("Pajares", citizen.getLocation());
        assertEquals("85", citizen.getId());

        citizen.setName("MariCarmen");
        assertNotEquals("Pepi", citizen.getName());
        assertEquals("MariCarmen", citizen.getName());

        citizen.setEmail("maaa15@mail.com");
        assertNotEquals("pepi@mail.com", citizen.getEmail());
        assertEquals("maaa15@mail.com", citizen.getEmail());

        citizen.setLocation("Ponga");
        assertNotEquals("Pajares", citizen.getLocation());
        assertEquals("Ponga", citizen.getLocation());
        
        citizen.setId("13");
        assertNotEquals("85", citizen.getId());
        assertEquals("13", citizen.getId());
    }

}