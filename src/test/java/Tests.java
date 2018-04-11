import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	agent.APIControllerTest.class,
	agent.ChangePasswordControllerTest.class,
	agent.FormControllerTest.class,
	agent.MainControllerTest.class,
	agent.ModelTest.class,
	//agent.RestControllerTest.class
})
public class Tests {
	@BeforeClass
	public static void setUp(){
		MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://admin:EIIASW2018$@ds127888.mlab.com:27888/loader_i1a_db"));
		mongo.getDatabase("loader_i1a_db").getCollection("loader_i1a_collection").drop();
		mongo.close();
	}
}
