package controller;

import agent.AgentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import repository.DBService;

/**
 * Created by guille on 20/02/2017.
 */
@Controller
public class ChangePasswordController {
    private final DBService service;

    @Autowired
    ChangePasswordController(DBService service) {
        this.service = service;
    }

    @RequestMapping(value = "/changep", method = RequestMethod.GET)
    public String changeGet() {
        return "changep";

    }

    @RequestMapping(value = "/changep", method = RequestMethod.POST)
    public String changePost(Model model, @RequestParam(value = "email") String email, @RequestParam(value = "old") String old, @RequestParam(value = "password") String password, @RequestParam(value = "password2") String password2) {
        AgentInfo user = service.getAgent(email, old);
        if (user == null) {
            model.addAttribute("bg", "background: #F00;");
            model.addAttribute("result", "The password is incorrect");

        } else if (!password.equals(password2)) {
            model.addAttribute("bg", "background: #F00;");
            model.addAttribute("result", "The passwords don't match");

        } else {
            service.updateInfo(user.getId(), old, password);
            model.addAttribute("bg", "background: #0F0;");
            model.addAttribute("result", "The password has been changed");
        }

        return "changep";

    }

}
