package controller;

import agent.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.DBService;
import org.springframework.stereotype.Controller;

import java.text.DateFormat;

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
    public String loginPost(Model model, @RequestParam(value = "login") String login, @RequestParam(value = "password") String password) {
        // If the combination of email and password is correct, the data of the user is returned
        // If not, 404 NOT FOUND is returned
        UserInfo user = service.getParticipant(login, password);

        if (user == null)
            return "usererror";
        else {
            model.addAttribute("name1", user.getFirstName());
            model.addAttribute("name2", user.getLastName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("address", user.getAddress());
            model.addAttribute("nationality", user.getNationality());
            model.addAttribute("polling", user.getPollingStation());
            String birthdate = DateFormat.getDateInstance().format(user.getBirthDate());
            model.addAttribute("birthdate", birthdate);

            //model.addAttribute("nif", citizen.NIF);
            return "info";
        }

    }
}
