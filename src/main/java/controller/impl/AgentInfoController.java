package controller.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import agent.AgentDTO;
import agent.AgentInfo;
import agent.AgentLogin;
import dbmanagement.DBService;

@RestController
public class AgentInfoController implements controller.AgentInfo {

    private final DBService service;

    @Autowired
    AgentInfoController(DBService service) {
        this.service = service;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AgentDTO> user(@RequestBody AgentLogin login) {
        // If the combination of id and password is correct, the data of the user is returned
        // If not, 404 NOT FOUND is returned

        AgentInfo user = service.getAgent(login.getId(), login.getPassword(), login.getKind());
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            AgentDTO agent = new AgentDTO(user);
            return new ResponseEntity<>(agent, HttpStatus.OK);
        }

    }

}