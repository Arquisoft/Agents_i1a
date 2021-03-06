package tests;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import agent.AgentInfo;
import agent.Application;
import dbmanagement.DBService;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("dbmanagement")
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class FormControllerTest {
    @Value("${local.server.port}")
    private int port;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DBService db;

    @Before
    public void setUp() throws Exception {
        URL base = new URL("http://localhost:" + port + "/");
        RestTemplate template = new TestRestTemplate();
        template.getForEntity(base.toString(), String.class);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testLoginPage() throws Exception {
        String message = mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Username:")))
                .andExpect(content().string(containsString("Password:")))
                .andExpect(content().string(containsString("Kind:")))
                .andReturn().toString();
        
        assertNotNull(message);
    }

    @Test
    public void testLoginCorrect() throws Exception {
        AgentInfo user = new AgentInfo("juan","juan123@uniovi.es", "juan123","333",1);
        db.insertUser(user);

        mockMvc.perform(post("/login")
                .param("login", "333")
                .param("password", "juan123")
                .param("kind","1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("name", equalTo("juan")))
                .andExpect(model().attribute("email", equalTo("juan123@uniovi.es")))
                .andExpect(model().attribute("kind",equalTo("person")))
                .andExpect(model().attribute("kindCode",equalTo(1)));
        AgentInfo retrieved = db.getAgent("333", "juan123",1);
        assertNotNull(retrieved);

        user = new AgentInfo("juan","juan123@uniovi.es", "juan123","444",2);
        db.insertUser(user);

        mockMvc.perform(post("/login")
                .param("login", "444")
                .param("password", "juan123")
                .param("kind","2"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("name", equalTo("juan")))
                .andExpect(model().attribute("email", equalTo("juan123@uniovi.es")))
                .andExpect(model().attribute("kind",equalTo("entity")))
                .andExpect(model().attribute("kindCode",equalTo(2)));
        retrieved = db.getAgent("444", "juan123",2);
        assertNotNull(retrieved);

        user = new AgentInfo("juan","juan123@uniovi.es", "juan123","555",3);
        db.insertUser(user);

        mockMvc.perform(post("/login")
                .param("login", "555")
                .param("password", "juan123")
                .param("kind","3"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("name", equalTo("juan")))
                .andExpect(model().attribute("email", equalTo("juan123@uniovi.es")))
                .andExpect(model().attribute("kind",equalTo("sensor")))
                .andExpect(model().attribute("kindCode",equalTo(3)));
        retrieved = db.getAgent("555", "juan123",3);
        assertNotNull(retrieved);
    }

    @Test
    public void testLoginIncorrect() throws Exception {
        String message = mockMvc.perform(post("/login")
                .param("login", "inco@rre.ct")
                .param("password", "user")
                .param("kind", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Invalid login details.")))
                .andReturn().toString();
        
        assertNotNull(message);
    }

}