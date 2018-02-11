package agent;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;
import java.util.Date;

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

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("repository")
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class FormControllerTest {
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
    public void testLoginPage() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Username:")))
                .andExpect(content().string(containsString("Password:")));
    }

    @Test
    public void testLoginCorrect() throws Exception {
        AgentInfo user = new AgentInfo("pass", "name", "surname", "macorrect@il.com", new Date());
        db.insertUser(user);

        mockMvc.perform(post("/login")
                .param("login", "macorrect@il.com")
                .param("password", "pass"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("name1", equalTo("name")))
                .andExpect(model().attribute("name2", equalTo("surname")))
                .andExpect(model().attribute("email", equalTo("macorrect@il.com")))
                .andExpect(content().string(containsString("Name:")))
                .andExpect(content().string(containsString("Birthdate:")));
    }

    @Test
    public void testLoginIncorrect() throws Exception {
        mockMvc.perform(post("/login")
                .param("login", "inco@rre.ct")
                .param("password", "user"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Invalid login details.")));
    }

}