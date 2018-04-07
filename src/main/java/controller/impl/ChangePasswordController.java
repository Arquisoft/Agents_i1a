package controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import agent.AgentDTO;
import dbmanagement.DBService;
import validator.ChangePasswordValidator;

@Controller
public class ChangePasswordController implements controller.ChangePassword {
	
	@Autowired
	private ChangePasswordValidator validator;
	
	private final DBService service;

	@Autowired
	ChangePasswordController(DBService service) {
		this.service = service;
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String changeGet(Model model) {
		model.addAttribute("agentDTO", new AgentDTO());
		return "changePassword";
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePost(Model model, @Validated AgentDTO agentDTO, BindingResult result) {
		validator.validate(agentDTO, result);
		if(result.hasErrors()) {
			return "changePassword";
		}
		service.updateInfo(agentDTO.getId(), agentDTO.getPassword(), agentDTO.getNewpassword());
		return "successChangePassword";

	}

}