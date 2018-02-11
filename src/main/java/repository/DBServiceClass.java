package repository;

import agent.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBServiceClass implements DBService {

    @Autowired
    private
    UserInfoRepository repository;

    @Override
    public boolean updateInfo(String id, String oldPass, String newPass) {
        UserInfo user = repository.findOne(id);
        if (user.getPassword().equals(oldPass)) {
            user.setPassword(newPass);
            repository.save(user);
            return true;
        } else
            return false;
    }

    @Override
    public UserInfo getParticipant(String email, String password) {
        UserInfo user = repository.findByEmail(email);
        if (user != null && user.getPassword().equals(password))
            return user;
        else
            return null;
    }

    @Override
    public void insertUser(UserInfo user) {
        repository.insert(user);
    }
}
