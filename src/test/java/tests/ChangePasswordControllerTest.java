package tests;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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

import agent.Agent;
import agent.AgentDTO;
import agent.AgentInfo;
import agent.Application;
import dbmanagement.DBService;
import validator.ChangePasswordValidator;

/**
 * Created by guille on 20/02/2017.
 */
@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("dbmanagement")
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class ChangePasswordControllerTest {
    @Value("${local.server.port}")
    private int port;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DBService db;
    
    @Autowired
    private ChangePasswordValidator validator;

    @Before
    public void setUp() throws Exception {
        URL base = new URL("http://localhost:" + port + "/");
        RestTemplate template = new TestRestTemplate();
        template.getForEntity(base.toString(), String.class);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    @Test
    public void supports() throws Exception {
    	assertTrue( validator.supports( AgentDTO.class ) );
    	assertFalse( validator.supports( Agent.class ));
    	assertFalse( validator.supports( AgentInfo.class ));
    }

    @Test
    public void testGetChangePasswordPage() throws Exception {
        String message = mockMvc.perform(get("/changePassword"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("passwform")))
                .andExpect(content().string(containsString("Go back to")))
                .andReturn().toString();
        
        assertNotNull(message);
    }

    @Test
    public void testPostChangePasswordSuccess() throws Exception {
        AgentInfo user = new AgentInfo("marta", "marta123@uniovi.es", "marta123","5",1);
        db.insertUser(user);

        mockMvc.perform(post("/changePassword")
                .param("id", "5")
                .param("password", "marta123")
                .param("newpassword", "123marta")
                .param("newpassword2", "123marta")
                .param("kind","1"))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors());
        AgentInfo retrieved = db.getAgent("5", "123marta",1);
        assertNotNull(retrieved);
        assertTrue(retrieved.getId().equals("5"));
        assertFalse(retrieved.getPassword().equals("marta123"));
        assertTrue(retrieved.getPassword().equals("123marta"));
   
    }

    @Test
    public void testPostChangePasswordFail1() throws Exception {
        AgentInfo user = new AgentInfo("julia", "julia123@uniovi.es", "julia123", "","6",1);
        db.insertUser(user);

        mockMvc.perform(post("/changePassword")
                .param("user", "6")
                .param("old", "julia123")
                .param("password", "123julia")
                .param("password2", "12julia")
                .param("kind","1"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors());
        AgentInfo retrieved = db.getAgent("6", "julia123",1);
        assertNotNull(retrieved);
        assertTrue(retrieved.getEmail().equals("julia123@uniovi.es"));
        assertTrue(retrieved.getPassword().equals("julia123"));
        assertFalse(retrieved.getPassword().equals("123julia"));
        assertFalse(retrieved.getPassword().equals("12julia"));

    }

    @Test
    public void testPostChangePasswordFail2() throws Exception {
        AgentInfo user = new AgentInfo("lucia", "lucia123@uniovi.es", "lucia123","8",1);
        db.insertUser(user);

        mockMvc.perform(post("/changePassword")
                .param("user", "8")
                .param("old", "lucia1")
                .param("password", "123lucia")
                .param("password2", "123lucia")
                .param("kind","1"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors());
        AgentInfo retrieved = db.getAgent("8", "lucia123",1);
        assertNotNull(retrieved);
        assertTrue(retrieved.getId().equals("8"));
        assertTrue(retrieved.getPassword().equals("lucia123"));
        assertFalse(retrieved.getPassword().equals("123lucia"));

    }
    
    @Test
    public void testPostChangePasswordFail3() throws Exception {
        AgentInfo user = new AgentInfo("lucia", "lucia123@uniovi.es", "lucia123","8",1);
        db.insertUser(user);

        mockMvc.perform(post("/changePassword")
                .param("user", "8")
                .param("old", "lucia123")
                .param("password", "123lucia")
                .param("password2", "13lucia")
                .param("kind", "1"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors());
        AgentInfo retrieved = db.getAgent("8", "lucia123",1);
        assertNotNull(retrieved);
        assertTrue(retrieved.getId().equals("8"));
        assertTrue(retrieved.getPassword().equals("lucia123"));
        assertFalse(retrieved.getPassword().equals("123lucia"));

    }
    
}