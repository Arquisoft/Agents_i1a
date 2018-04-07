package agent;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import org.springframework.web.context.WebApplicationContext;

import dbmanagement.DBService;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("dbmanagement")
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class APIControllerTest {
    @Value("${local.server.port}")
    private int port;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DBService db;

    @Before
    public void setUp() throws Exception {
        new URL("http://localhost:" + port + "/");
        //new MongoClientURI("mongodb://admin:EIIASW2018$@ds127888.mlab.com:27888/loader_i1a_db");
        //noinspection deprecation
        db.wipeAll();
        new TestRestTemplate();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testDatabase() throws Exception {
        AgentInfo user = new AgentInfo("paco",  "paco123@uniovi.es", "paco123", "Oviedo", "3",1);
        db.insertUser(user);
        AgentInfo userFromDB = db.getAgent("3", "paco123",1);
        assertThat(user.getId(), equalTo(userFromDB.getId()));
        assertThat(user.getPassword(), equalTo(userFromDB.getPassword()));
        assertThat(user.getKind(), equalTo(userFromDB.getKind()));

        boolean update = db.updateInfo(userFromDB.getId(), "paco123", "123paco");
        userFromDB = db.getAgent("3", "123paco",1);
        assertThat(update, equalTo(true));
        assertThat(userFromDB, notNullValue());
        assertThat("3", equalTo(userFromDB.getId()));
        assertThat("123paco", equalTo(userFromDB.getPassword()));

        update = db.updateInfo(userFromDB.getId(), "paco123", "123pepe");
        assertThat(update, equalTo(false));

        String[] info = {"paco","paco123@uniovi.es","paco123","Oviedo","3","2"};
        AgentInfo user2 = new AgentInfo(info);

        assertFalse(user.getIdautogenerado() == user2.getIdautogenerado());
        assertFalse(user.equals(user2));
        assertFalse(user.hashCode() == user2.hashCode());
    }

    @Test
    public void postTestUser() throws Exception {
        AgentInfo user = new AgentInfo("maria", "maria123@uniovi.es"
                , "maria123","4",1);
        db.insertUser(user);
        mockMvc.perform(post("/user")
                .content("{ \"id\":\"4\",\"password\": \"maria123\", \"kind\": \"1\"}")
                .contentType(new MediaType(MediaType.APPLICATION_JSON.getType(),
                        MediaType.APPLICATION_JSON.getSubtype(),
                        Charset.forName("utf8"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(content().json("{\"name\":\"maria\"," +
                        "\"id\":\"4\",\"email\":maria123@uniovi.es,\"kind\":'1'}")
                );
        assertNotNull(db.getAgent("4","maria123",1));
    }

    @Test
    public void postTestNotFoundUser() throws Exception {
        mockMvc.perform(post("/user")
                .content("{ \"id\": \"jaajaaGarcia\", \"password\": \"nothepassword\", \"kind\": \"4\"}")
                .contentType(new MediaType(MediaType.APPLICATION_JSON.getType(),
                        MediaType.APPLICATION_JSON.getSubtype(),
                        Charset.forName("utf8"))))
                .andExpect(status().isNotFound()
                );
        assertNull(db.getAgent("jaajaaGarcia","nothepassword", 4));
    }


}
