package cucumber.steps;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.List;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import agent.AgentInfo;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dbmanagement.impl.DBServiceClass;

public class LoginSteps {
	
	@Mock
	private DBServiceClass dbService;
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	private ResultActions result;
	
	private User user;

	@Given("^a list of agents:$")
	public void a_list_of_agents(List<AgentInfo> agents) throws Throwable {
		MockitoAnnotations.initMocks(this);
		
		for (AgentInfo a : agents) {
			when(dbService.existAgent(a.getId(), a.getPassword(), a.getKind())).thenReturn(true);
		}
	}

	@When("^I login with name \"(.+)\" , password \"(.+)\" and kind \"(.+)\"$")
	public void i_login_with_name_and_password(String name, String password, String kind) throws Throwable {
		user = new User(name, password, kind);
		
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		result = mockMvc.perform(post("/login")
                .param("login", name)
                .param("password", password)
                .param("kind", kind));
	}

	@Then("^I can see my data$")
	public void i_receive_a_welcome_message() throws Throwable {
        result.andExpect(model().attribute("id",equalTo(user.name)));
        result.andExpect(model().attribute("kindCode",equalTo(Integer.parseInt(user.kind))));
        result.andExpect(content().string(containsString("Agent Information")));
	}
	
	@Then("^I see an error message$")
	public void i_see_an_error_message() throws Throwable {
	    result.andExpect(content().string(containsString("Invalid login details.")));
	}
	
	private static class User {
		public String name, password, kind;
		public User(String name, String password, String kind) {
			this.name = name;
			this.password = password;
			this.kind = kind;
		}
	}
}
