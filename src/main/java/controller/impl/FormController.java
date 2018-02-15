package controller.impl;

import agent.AgentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import DBmanagement.DBService;
import org.springframework.stereotype.Controller;

/**
 * Created by guille on 19/02/2017.
 */
@Controller
public class FormController {
    private final DBService service;

    @Autowired
    FormController(DBService service) {
        this.service = service;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(Model model, @RequestParam(value = "login") String username, @RequestParam(value = "password") String password,
                            @RequestParam(value = "kind") String kind) {
        // If the combination of email and password is correct, the data of the user is returned
        // If not, 404 NOT FOUND is returned
        AgentInfo user = service.getAgent(username, password, kind);

        if (user == null)
            return "usererror";
        else {
            model.addAttribute( "name", user.getName());
            model.addAttribute( "name", user.getUsername());
            model.addAttribute( "location", user.getLocation());
            model.addAttribute( "email", user.getEmail());
            model.addAttribute( "id", user.getNIF());
            model.addAttribute( "kind", user.getKind());
            model.addAttribute( "kindCode", user.getKindCode());

            return "info";
        }

    }
}