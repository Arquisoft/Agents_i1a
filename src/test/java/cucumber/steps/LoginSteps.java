package cucumber.steps;

import static org.mockito.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import agent.AgentInfo;
import controller.impl.MainController;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dbmanagement.impl.DBServiceClass;

public class LoginSteps {
	
	@Mock
	private DBServiceClass dbService;
	
	@InjectMocks
	private MainController mainController;
	
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
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
		result = mockMvc.perform(post("/login")
                .param("login", name)
                .param("password", password)
                .param("kind", kind));
	}

	@Then("^I can see my data$")
	public void i_can_see_my_data() throws Throwable {
        result.andExpect(content().string((contains(user.name))));
        result.andExpect(content().string((contains(user.kind))));
        result.andExpect(content().string((contains("Agent Information"))));
	}
	
	@Then("^I see an error message$")
	public void i_see_an_error_message() throws Throwable {
	    result.andExpect(content().string((contains("Invalid login details."))));
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