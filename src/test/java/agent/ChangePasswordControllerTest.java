package agent;

import main.Application;
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
import repository.DBService;

import java.net.URL;
import java.util.Date;

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
@ComponentScan("repository")
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class ChangePasswordControllerTest {
    @Value("${local.server.port}")
    private int port;

    private URL base;
    private RestTemplate template;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DBService db;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
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
        AgentInfo user = new AgentInfo("pepe", "pepe123@uniovi.es", "person","1","pepe123");
        db.insertUser(user);

        mockMvc.perform(post("/changep")
                .param("email", "pepe123@uniovi.es")
                .param("old", "pepe123")
                .param("password", "123pepe")
                .param("password2", "123pepe")
                .param("kind","person"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("result", equalTo("The password has been changed")))
                .andExpect(model().attribute("bg", equalTo("background: #0F0;")))
                .andExpect(content().string(containsString("passwform")))
                .andExpect(content().string(containsString("result")))
                .andExpect(content().string(containsString("Go back to")));
        AgentInfo retrieved = db.getAgent("pepe123@uniovi.es", "newpass","person");
        assertNotNull(retrieved);
        assertTrue(retrieved.getEmail().equals("pepe123@uniovi.es"));
        assertTrue(!retrieved.getPassword().equals("123pepe"));
        assertTrue(retrieved.getPassword().equals("123pepe"));

    }

    @Test
    public void testPostChangePasswordFail1() throws Exception {
        AgentInfo user = new AgentInfo("pepe", "pepe123@uniovi.es", "person","1","pepe123");
        db.insertUser(user);

        mockMvc.perform(post("/changep")
                .param("email", "test2@mail.com")
                .param("old", "pass")
                .param("password", "newpass1")
                .param("password2", "newpass"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("result", equalTo("The passwords don't match")))
                .andExpect(model().attribute("bg", equalTo("background: #F00;")))
                .andExpect(content().string(containsString("passwform")))
                .andExpect(content().string(containsString("result")))
                .andExpect(content().string(containsString("Go back to")));
        AgentInfo retrieved = db.getAgent("test2@mail.com", "pass","person");
        assertNotNull(retrieved);
        assertTrue(retrieved.getEmail().equals("test2@mail.com"));
        assertTrue(retrieved.getPassword().equals("pass"));
        assertTrue(!retrieved.getPassword().equals("newpass"));
        assertTrue(!retrieved.getPassword().equals("newpass2"));

    }

    @Test
    public void testPostChangePasswordFail2() throws Exception {
        AgentInfo user = new AgentInfo("pepe", "pepe123@uniovi.es", "person","1","pepe123");
        db.insertUser(user);

        mockMvc.perform(post("/changep")
                .param("email", "test3@mail.com")
                .param("old", "wrongpass")
                .param("password", "new")
                .param("password2", "new"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("result", equalTo("The password is incorrect")))
                .andExpect(model().attribute("bg", equalTo("background: #F00;")))
                .andExpect(content().string(containsString("passwform")))
                .andExpect(content().string(containsString("result")))
                .andExpect(content().string(containsString("Go back to")));
        AgentInfo retrieved = db.getAgent("test3@mail.com", "pass","person");
        assertNotNull(retrieved);
        assertTrue(retrieved.getEmail().equals("test3@mail.com"));
        assertTrue(retrieved.getPassword().equals("pass"));
        assertTrue(!retrieved.getPassword().equals("new"));

    }
}
