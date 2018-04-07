package dbmanagement;

import agent.Agent;

public interface DBService {

    boolean updateInfo(String id, String oldPass, String newPass);
    Agent getAgent(String id, String password, int kind);
    void insertUser(Agent user);

}
