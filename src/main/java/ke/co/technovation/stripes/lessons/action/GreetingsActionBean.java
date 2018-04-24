package ke.co.technovation.stripes.lessons.action;

import org.apache.log4j.Logger;

import ke.co.technovation.stripes.model.Greeting;
import ke.co.technovation.stripes.service.GreetingService;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;


/**
 * 
 * @author timothymwangi
 * 
 * This is a basic Action class that processes
 * requests.
 *
 */
public class GreetingsActionBean extends BaseActionBean{
	
	private Logger logger = Logger.getLogger(getClass());
	
	@SpringBean
	private GreetingService greetingService;
	
	
	@Before(on={"sayHelloWorld", "sayGoodByeWorld"}, stages=LifecycleStage.BindingAndValidation)
	public void initialize(){
		try{
			greetingService.init();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * This method is called by default when we
	 * invoke this action class.
	 * 
	 * This is because it is annotated with the
	 * annotation net.sourceforge.stripes.action.DefaultHandler
	 * 
	 * In Action beans, default handlers are not mandatory,
	 * and only one method can be annotated with @DefaultHandler.
	 * 
	 * 
	 * Notice Action bean class methods return net.sourceforge.stripes.action.Resolution
	 * There are many implementations of net.sourceforge.stripes.action.Resolution
	 * which you will learn as this course becomes more advanced.
	 * 
	 */
	@DefaultHandler
	public Resolution sayHelloWorld() {
		
		Greeting helloGreeting  = greetingService.getHelloGreeting();
		if(helloGreeting!=null)
			return new StreamingResolution("text/html", helloGreeting.getGreeting());
		else
			return new StreamingResolution("text/html",  "Sorry. No hello greeting found! Are there records in the db ?");
	}
	
	/**
	 * This method does not have  @DefaultHandler annotation.
	 * You must explicitly specify that you want to call it from the URl.
	 * @return
	 */
	public Resolution sayGoodByeWorld() {
		Greeting goodByeGreeting = greetingService.getGoodbyeGreeting();
		
		if(goodByeGreeting!=null)
			return new StreamingResolution("text/html", goodByeGreeting.getGreeting());
		else
			return new StreamingResolution("text/html", "Sorry. No goodbye greeting found! Are there records in the db ?");
	}

}
