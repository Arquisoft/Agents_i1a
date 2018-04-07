package dbmanagement.impl;

import dbmanagement.DBService;
import dbmanagement.UserInfoRepository;
import agent.Agent;
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
        Agent user = repository.findById(id);
        if (user.getPassword().equals(oldPass)) {
            user.setPassword(newPass);
            repository.save(user);
            return true;
        } else
            return false;
    }

    @Override
    public Agent getAgent(String id, String password, int kind) {
        Agent user = repository.findById(id);

        if (user != null && user.getPassword().equals(password) && user.getKind() == kind)
            return user;
        else
            return null;
    }

    @Override
    public void insertUser(Agent user) {
        repository.insert(user);
    }
}
