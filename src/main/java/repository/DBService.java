package repository;

import agent.AgentInfo;

public interface DBService {

    boolean updateInfo(String id, String oldPass, String newPass);
    AgentInfo getAgent(String email, String password, String kind);
    void insertUser(AgentInfo user);
}
