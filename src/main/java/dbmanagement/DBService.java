package dbmanagement;

import agent.AgentInfo;

public interface DBService {

    boolean updateInfo(String id, String oldPass, String newPass);
    AgentInfo getAgent(String id, String password, int kind);
    void insertUser(AgentInfo user);
    void wipeAll();
}
