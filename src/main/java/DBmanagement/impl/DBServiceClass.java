package DBmanagement.impl;

import DBmanagement.DBService;
import DBmanagement.UserInfoRepository;
import agent.AgentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBServiceClass implements DBService {

    @Autowired
    private UserInfoRepository repository;

    public DBServiceClass(UserInfoRepository repository) {
        this.repository = repository;
    }

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
    public AgentInfo getAgent(String username, String password, String kind) {
        AgentInfo user = repository.findByUsername(username);
        if (user != null && user.getPassword().equals(password) && user.getKind().equals(kind))
            return user;
        else
            return null;
    }

    @Override
    public void insertUser(AgentInfo user) {
        repository.insert(user);
    }
}
