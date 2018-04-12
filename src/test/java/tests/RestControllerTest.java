package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;

import org.json.JSONObject;
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
import org.springframework.test.web.servlet.MvcResult;
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
	    public void testReturnAgentInfo() throws Exception {
	    	
	    	String requestJson = 
					"{ " + 
						"\"id\":\"3\"," + 
						"\"password\":\"123paco\"," + 
						"\"kind\":\"1\"" +
					"}";
			
			MvcResult result = mockMvc.perform(post("/restLogin").contentType( MediaType.APPLICATION_JSON_VALUE )
				.content( requestJson ))
				.andExpect(status().isOk())
				.andReturn();
			
			String agent = result.getResponse().getContentAsString();
			JSONObject agentInfo = new JSONObject( agent );
			
	    	assertNotNull( agent );
	    	assertTrue( agentInfo.get("name").equals("paco") );
	    	assertTrue( agentInfo.get("id").equals("3") );
	    	assertTrue( agentInfo.get("password").equals("123paco") );
	    	assertTrue( agentInfo.get("kind").equals( 1 ) );
	    }
	    
	    @Test
	    public void testReturnAgentInfoFail() throws Exception {
	    	
	    	String requestJson = 
					"{ " + 
						"\"id\":\"33333\"," + 
						"\"password\":\"123paco\"," + 
						"\"kind\":\"1\"" +
					"}";
			
			String message = mockMvc.perform(post("/restLogin").contentType( MediaType.APPLICATION_JSON_VALUE )
				.content( requestJson ))
				.andExpect(status().isNotFound())
				.andReturn().toString();
			
			assertNotNull( message );
			
	    }
	    
	    @Test
	    public void testOkUser() throws Exception {

			String requestJson = 
					"{ " + 
						"\"id\":\"3\"," + 
						"\"password\":\"123paco\"," + 
						"\"kind\":\"1\"" +
					"}";
			
			MvcResult result = mockMvc.perform(post("/restAgentInfo").contentType( MediaType.APPLICATION_JSON_VALUE )
				.content( requestJson ))
				.andExpect(status().isOk())
				.andReturn();
			
			String agent = result.getResponse().getContentAsString();
			JSONObject agentInfo = new JSONObject( agent );
			
	    	assertNotNull( agent );
	    	assertTrue( agentInfo.get("name").equals("paco") );
	    	assertTrue( agentInfo.get("id").equals("3") );
	    	assertTrue( agentInfo.get("password").equals("123paco") );
	    	assertTrue( agentInfo.get("kind").equals( 1 ) );
	    }
	    
}
