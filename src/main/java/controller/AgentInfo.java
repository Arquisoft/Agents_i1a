package controller;

import agent.AgentDTO;
import agent.AgentLogin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AgentInfo {

    ResponseEntity<AgentDTO> user(@RequestBody AgentLogin login);

}
