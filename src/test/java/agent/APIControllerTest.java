package agent;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;
import java.nio.charset.Charset;
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
import org.springframework.http.MediaType;
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
public class APIControllerTest {
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
        //noinspection deprecation
        template = new TestRestTemplate();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testDatabase() throws Exception {
        AgentInfo user = new AgentInfo("pass2", "name", "surname", "ma@il2.com", new Date());
        db.insertUser(user);
        AgentInfo userFromDB = db.getAgent("ma@il2.com", "pass2");
        assertThat(user.getEmail(), equalTo(userFromDB.getEmail()));
        assertThat(user.getPassword(), equalTo(userFromDB.getPassword()));

        boolean update = db.updateInfo(userFromDB.getId(), "pass2", "pass3");
        userFromDB = db.getAgent("ma@il2.com", "pass3");
        assertThat(update, equalTo(true));
        assertThat(userFromDB, notNullValue());
        assertThat("ma@il2.com", equalTo(userFromDB.getEmail()));
        assertThat("pass3", equalTo(userFromDB.getPassword()));


        update = db.updateInfo(userFromDB.getId(), "pass2", "pass3");
        assertThat(update, equalTo(false));

    }

    @Test
    public void postTestUser() throws Exception {
        AgentInfo user = new AgentInfo("pass", "name", "surname", "ma@il.com", new Date());
        db.insertUser(user);
        mockMvc.perform(post("/user")
                .content("{ \"login\": \"ma@il.com\", \"password\": \"pass\"}")
                .contentType(new MediaType(MediaType.APPLICATION_JSON.getType(),
                        MediaType.APPLICATION_JSON.getSubtype(),
                        Charset.forName("utf8"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(content().json("{\"firstName\":\"name\",\"lastName\":\"surname\",\"age\":0,\"ID\":null,\"email\":\"ma@il.com\"}")
                );
    }

    @Test
    public void postTestNotFoundUser() throws Exception {
        mockMvc.perform(post("/user")
                .content("{ \"login\": \"ma@il.com\", \"password\": \"nothepassword\"}")
                .contentType(new MediaType(MediaType.APPLICATION_JSON.getType(),
                        MediaType.APPLICATION_JSON.getSubtype(),
                        Charset.forName("utf8"))))
                .andExpect(status().isNotFound()
                );

    }


}
