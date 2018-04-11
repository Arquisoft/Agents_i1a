package tests;

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

import agent.Application;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("dbmanagement")
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class RestControllerTest {
	
	 @Value("${local.server.port}")
	    private int port;

	 	private MockMvc mockMvc;

	    @Autowired
	    private WebApplicationContext context;

	    @Before
	    public void setUp() throws Exception {
	        URL base = new URL("http://localhost:" + port + "/");
	        RestTemplate template = new TestRestTemplate();
	        template.getForEntity(base.toString(), String.class);
	        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	    }

	    @Test
	    public void testOkUser() throws Exception {
	    	
//	    	HttpHeaders header = new HttpHeaders();
//			header.setContentType(MediaType.APPLICATION_JSON);
//	    	
//	    	JSONObject request = new JSONObject();
//			request.put("name", "8");
//	        request.put("password", "lucia123");
//			request.put("kind", "1");
//
//			HttpEntity<String> entity = new HttpEntity<String>(request.toString(), header);
//	    	
//			//Agent agent = new RestTemplate().exchange("/restAgentInfo", HttpMethod.POST, entity, Agent.class).getBody();
//			Agent agent = (Agent) mockMvc.perform(post("/restAgentInfo").requestAttr("name", entity))
//				.andExpect(status().isOk())
//				.andReturn();
//	    	assertNotNull( agent );
//	    	assertTrue( agent.getId().equals( "8" ) );
//	    	assertTrue( agent.getPassword().equals( "lucia123" ) );
//	    	assertTrue( agent.getKind() == 1 );
	    	 
	    }
	    
	    @Test
	    public void testReturnAgentInfo() throws Exception {
	    	
	    }
	    
}
