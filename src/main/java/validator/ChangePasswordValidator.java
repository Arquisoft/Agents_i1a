package validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import agent.AgentDTO;
import agent.AgentInfo;
import dbmanagement.DBService;

@Component
public class ChangePasswordValidator implements Validator {

	@Autowired
	private DBService service;

	@Override
	public boolean supports(Class<?> aClass) {
		return AgentDTO.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "kind", "error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newpassword", "error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newpassword2", "error.empty");

		AgentDTO agent = (AgentDTO) target;
		if(!agent.getKind().isEmpty()) {
			AgentInfo user = service.getAgent(agent.getId(), agent.getPassword(), Integer.parseInt(agent.getKind()));
			if (user == null) {
				errors.rejectValue("password", "error.password");

			} else if (!agent.getNewpassword().equals(agent.getNewpassword2())) {
				errors.rejectValue("newpassword", "error.passwords.matching");
			}
		}
		
	}

}
