package repository;

import agent.AgentInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends MongoRepository<AgentInfo, String> {

    AgentInfo insert(AgentInfo user);

    AgentInfo findByEmail(String email);


}
