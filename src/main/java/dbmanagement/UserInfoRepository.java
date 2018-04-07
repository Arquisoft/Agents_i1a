package dbmanagement;

import agent.Agent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends MongoRepository<Agent, String> {

    Agent insert(Agent user);
    Agent findById(String id);

}
