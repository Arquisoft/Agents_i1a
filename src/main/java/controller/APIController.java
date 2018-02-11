package controller;


import agent.AgentDTO;
import agent.AgentLogin;
import agent.AgentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.DBService;

@RestController
public class APIController {

    private final DBService service;

    @Autowired
    APIController(DBService service) {
        this.service = service;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AgentDTO> user(@RequestBody AgentLogin login) {
        // If the combination of email and password is correct, the data of the user is returned
        // If not, 404 NOT FOUND is returned

        AgentInfo user = service.getAgent(login.getLogin(), login.getPassword(), login.getKind());
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            AgentDTO citizen = new AgentDTO(user);
            return new ResponseEntity<>(citizen, HttpStatus.OK);
        }

    }

}