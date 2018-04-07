package controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import agent.AgentLogin;
import dbmanagement.impl.DBServiceClass;

@RestController
public class AgentRestController {
	
	@Autowired
	private DBServiceClass dbService;

	@RequestMapping(value="/restLogin", method=RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<agent.AgentInfo> okUser(@RequestBody AgentLogin receivedInfo) {
		agent.AgentInfo agent = dbService.getAgent(receivedInfo.getId(), receivedInfo.getPassword(), receivedInfo.getKind());
		
		if(agent == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else return new ResponseEntity<>(agent, HttpStatus.OK);
	}
	
	@RequestMapping(value="/restAgentInfo", method=RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public agent.AgentInfo returnAgentInfo(@RequestBody AgentLogin receivedInfo) {
		agent.AgentInfo agent = dbService.findById(receivedInfo.getId());
		return agent;
	}
}
