package agent;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;
import java.nio.charset.Charset;

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

import DBmanagement.DBService;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("DBmanagement")
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class APIControllerTest {
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
        //this.base = new MongoClientURI("mongodb://admin:EIIASW2018$@ds127888.mlab.com:27888/loader_i1a_db");
        //noinspection deprecation
        template = new TestRestTemplate();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testDatabase() throws Exception {
        AgentInfo user = new AgentInfo("paco", "pacoperson", "paco123@uniovi.es", "person","3","paco123");
        db.insertUser(user);
        AgentInfo userFromDB = db.getAgent("pacoperson", "paco123","person");
        assertThat(user.getUsername(), equalTo(userFromDB.getUsername()));
        assertThat(user.getPassword(), equalTo(userFromDB.getPassword()));
        assertThat(user.getKind(), equalTo(userFromDB.getKind()));

        boolean update = db.updateInfo(userFromDB.getId(), "paco123", "123paco");
        userFromDB = db.getAgent("pacoperson", "123paco","person");
        assertThat(update, equalTo(true));
        assertThat(userFromDB, notNullValue());
        assertThat("pacoperson", equalTo(userFromDB.getUsername()));
        assertThat("123paco", equalTo(userFromDB.getPassword()));


        update = db.updateInfo(userFromDB.getId(), "paco123", "123pepe");
        assertThat(update, equalTo(false));

    }

    @Test
    public void postTestUser() throws Exception {
        AgentInfo user = new AgentInfo("maria", "merimeri", "maria123@uniovi.es", "person","4","maria123");
        db.insertUser(user);
        mockMvc.perform(post("/user")
                .content("{ \"login\": \"merimeri\", \"password\": \"maria123\", \"kind\": \"person\"}")
                .contentType(new MediaType(MediaType.APPLICATION_JSON.getType(),
                        MediaType.APPLICATION_JSON.getSubtype(),
                        Charset.forName("utf8"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(content().json("{\"name\":\"maria\"," +
                        "\"location\":\"\",\"email\":\"maria123@uniovi.es\",\"kind\":\"person\", " +
                        "\"kindcode\":\"1\" }")
                );
    }

    @Test
    public void postTestNotFoundUser() throws Exception {
        mockMvc.perform(post("/user")
                .content("{ \"login\": \"jaajaaGarcia\", \"password\": \"nothepassword\"}")
                .contentType(new MediaType(MediaType.APPLICATION_JSON.getType(),
                        MediaType.APPLICATION_JSON.getSubtype(),
                        Charset.forName("utf8"))))
                .andExpect(status().isNotFound()
                );

    }


}
