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
    public AgentInfo getAgent(String email, String password, String kind) {
        AgentInfo user = repository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)
               /*  && user.getKind().equals(kind)*/)
            return user;
        else
            return null;
    }

    @Override
    public void insertUser(AgentInfo user) {
        repository.insert(user);
    }
}
