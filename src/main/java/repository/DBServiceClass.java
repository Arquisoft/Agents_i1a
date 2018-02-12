package repository;

import agent.AgentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBServiceClass implements DBService {

    @Autowired
    private UserInfoRepository repository;

    @Override
    public boolean updateInfo(String id, String oldPass, String newPass) {
        AgentInfo user = repository.findOne(id);
        if (user.getPassword().equals(oldPass)) {
            user.setPassword(newPass);
            repository.save(user);
            return true;
        } else
            return false;
    }

    @Override
    public AgentInfo getAgent(String login, String password, String kind) {
        AgentInfo user = repository.findByCombination(login, kind);
        if (user != null && user.getPassword().equals(password))
            return user;
        else
            return null;
    }

    @Override
    public void insertUser(AgentInfo user) {
        repository.insert(user);
    }
}
