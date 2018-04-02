package controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import agent.AgentDTO;

public interface ChangePassword {

    String changeGet(Model model);
    String changePost(Model model,AgentDTO agent, BindingResult result);

}
