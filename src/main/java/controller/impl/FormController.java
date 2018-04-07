package controller.impl;

import agent.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import dbmanagement.DBService;
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
    public String loginPost(Model model, @RequestParam(value = "login") String id, @RequestParam(value = "password") String password,
                            @RequestParam(value = "kind") String kind) {
        // If the combination of id and password is correct, the data of the user is returned
        // If not, 404 NOT FOUND is returned
        Agent user = service.getAgent(id, password, Integer.parseInt(kind));

        if (user == null)
            return "usererror";
        else {
            model.addAttribute( "name", user.getName());
            model.addAttribute( "location", user.getLocation());
            model.addAttribute( "email", user.getEmail());
            model.addAttribute( "id", user.getId());
            model.addAttribute( "kindCode", user.getKind());
            if (user.getKind() == 1) model.addAttribute( "kind", "person");
            else if (user.getKind() == 2) model.addAttribute( "kind", "entity");
            else if (user.getKind() == 3) model.addAttribute( "kind", "sensor");

            return "getAgentInfo";
        }

    }
}
