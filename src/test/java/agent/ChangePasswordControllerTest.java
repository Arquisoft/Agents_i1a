package agent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import DBmanagement.DBService;

import java.net.URL;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by guille on 20/02/2017.
 */
@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("DBmanagement")
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class ChangePasswordControllerTest {
    @Value("${local.server.port}")
    private int port;

    private URL base;
    //private MongoClientURI base;
    private RestTemplate template;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DBService db;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
        //this.base = new MongoClientURI("mongodb://Loader_i1a:EIIASW2018$@ds127888.mlab.com:27888/loader_i1a_db");
        template = new TestRestTemplate();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testGetChangePasswordPage() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        mockMvc.perform(get("/changep"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("passwform")))
                .andExpect(content().string(containsString("Go back to")));
    }

    @Test
    public void testPostChangePasswordSuccess() throws Exception {
        // If this test fails, tru clearing the database
        AgentInfo user = new AgentInfo("marta", "martuqui", "marta123@uniovi.es", "person","5","marta123");
        db.insertUser(user);

        mockMvc.perform(post("/changep")
                .param("username", "martuqui")
                .param("email", "marta123@uniovi.es")
                .param("old", "marta123")
                .param("password", "123marta")
                .param("password2", "123marta")
                .param("kind","person"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("result", equalTo("The password has been changed")))
                .andExpect(model().attribute("bg", equalTo("background: #0F0;")))
                .andExpect(content().string(containsString("passwform")))
                .andExpect(content().string(containsString("result")))
                .andExpect(content().string(containsString("Go back to")));
        AgentInfo retrieved = db.getAgent("martuqui", "123marta","person");
        assertNotNull(retrieved);
        assertTrue(retrieved.getUsername().equals("martuqui"));
        assertTrue(!retrieved.getPassword().equals("marta123"));
        assertTrue(retrieved.getPassword().equals("123marta"));

    }

    @Test
    public void testPostChangePasswordFail1() throws Exception {
        AgentInfo user = new AgentInfo("julia", "julip", "julia123@uniovi.es", "person","6","julia123");
        db.insertUser(user);

        mockMvc.perform(post("/changep")
                .param("username", "julip")
                .param("email", "julia123@uniovi.es")
                .param("old", "julia123")
                .param("password", "123julia")
                .param("password2", "12julia")
                .param("kind","person"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("result", equalTo("The passwords don't match")))
                .andExpect(model().attribute("bg", equalTo("background: #F00;")))
                .andExpect(content().string(containsString("passwform")))
                .andExpect(content().string(containsString("result")))
                .andExpect(content().string(containsString("Go back to")));
        AgentInfo retrieved = db.getAgent("julip", "julia123","person");
        assertNotNull(retrieved);
        assertTrue(retrieved.getEmail().equals("julip"));
        assertTrue(retrieved.getPassword().equals("julia123"));
        assertTrue(!retrieved.getPassword().equals("123julia"));
        assertTrue(!retrieved.getPassword().equals("12julia"));

    }

    @Test
    public void testPostChangePasswordFail2() throws Exception {
        AgentInfo user = new AgentInfo("lucia", "lalaland","lucia123@uniovi.es", "person","8","lucia123");
        db.insertUser(user);

        mockMvc.perform(post("/changep")
                .param("username", "lalaland")
                .param("email", "lucia123@uniovi.es")
                .param("old", "lucia1")
                .param("password", "123lucia")
                .param("password2", "123lucia")
                .param("kind","person"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("result", equalTo("The password is incorrect")))
                .andExpect(model().attribute("bg", equalTo("background: #F00;")))
                .andExpect(content().string(containsString("passwform")))
                .andExpect(content().string(containsString("result")))
                .andExpect(content().string(containsString("Go back to")));
        AgentInfo retrieved = db.getAgent("lalaland", "lucia123","person");
        assertNotNull(retrieved);
        assertTrue(retrieved.getUsername().equals("lalaland"));
        assertTrue(retrieved.getPassword().equals("lucia123"));
        assertTrue(!retrieved.getPassword().equals("123lucia"));

    }
}
