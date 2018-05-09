package cucumber.steps;

import static org.mockito.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import agent.AgentInfo;
import controller.impl.MainController;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dbmanagement.impl.DBServiceClass;

public class ChangePasswordSteps {
	@InjectMocks
	private MainController mainController;
	
	private MockMvc mockMvc;
	private ResultActions result;
	
	@When("^I login with \"(.+)\" , \"(.+)\" and \"(.+)\"$")
	public void i_login_with_name_and_password(String name, String password, String kind) throws Throwable {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
		
		result = mockMvc.perform(post("/login")
                .param("login", name)
                .param("password", password)
                .param("kind", kind));
	}
	
	@When("^I introduce name \"(.+)\" , old password \"(.+)\" , kind \"(.+)\" and new password \"(.+)\"$")	
	public void i_introduce_name_kind_oldpassword(String name, String oldpwd, String kind, String newpwd) throws Throwable {
		result = mockMvc.perform(post("/changePassword")
	                .param("id", name)
	                .param("password", oldpwd)
	                .param("newpassword", newpwd)
	                .param("newpassword2", newpwd)
	                .param("kind", kind));
	}
	
	@And("^I click in change password$")
	public void i_click_in_change_password() throws Throwable {
		mockMvc.perform(get("/changePassword"));
	}

	@Then("^I see a correctly change message$")
	public void i_see_a_correctly_change_message() throws Throwable {
		 result.andExpect(content().string(contains("Invalid login details.")));
	}
}
