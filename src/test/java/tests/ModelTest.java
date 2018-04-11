package tests;

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

import agent.Agent;
import agent.AgentDTO;
import agent.AgentInfo;
import agent.Application;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("dbmanagement")
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class ModelTest {
    @Value("${local.server.port}")
    private int port;

    @Before
    public void setUp() throws Exception {
        new URL("http://localhost:" + port + "/");
        new TestRestTemplate();
    }

    @Test
    public void testAgentInfo() throws Exception {
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

        AgentInfo user3 = new AgentInfo(name,"n",password,location,NIF,kind);
        
        assertFalse(user.hashCode() == user2.hashCode());
        assertFalse(user.equals(user2));
        assertFalse(user.equals(null));
        assertTrue(user.equals(user));
        assertFalse(user3.equals(user2));
    }
    
    @Test
    public void testAgent() throws Exception{
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
        String[] data = {name, password, email, location, NIF, String.valueOf(kind)};

        Agent user = new Agent(name,email,password,NIF,kind);
        Agent user2 = new Agent(name,email,password,location,NIF,kind);
        Agent user3 = new Agent(data);
        Agent user4 = new Agent();
        
        assertFalse(user.equals(user2));
        assertFalse(user2.equals(user3));
        assertFalse(user3.equals(user4));
        
        assertTrue(user.getPassword().equals(password));
        user.setPassword("nueva");
        assertFalse(user.getPassword().equals(password));
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

        assertEquals("Agent{idautogenerado='null', name='name'" +
                ", email='mail@mail.com', password='nueva', location='', id='10203040A'" +
                ", kind=1}", user.toString());

        assertEquals("Agent{idautogenerado='null', name='name'" +
                ", email='mail@mail.com', password='password', location='caceres', id='10203040A'" +
                ", kind=1}", user2.toString());

        assertFalse(user.hashCode() == user2.hashCode());
        assertFalse(user.equals(user2));
        assertFalse(user.equals(null));
        assertTrue(user.equals(user));
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