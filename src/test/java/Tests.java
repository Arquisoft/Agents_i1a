import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	agent.APIControllerTest.class,
	agent.ChangePasswordControllerTest.class,
	agent.FormControllerTest.class,
	agent.MainControllerTest.class,
	agent.ModelTest.class
})
public class Tests { }
