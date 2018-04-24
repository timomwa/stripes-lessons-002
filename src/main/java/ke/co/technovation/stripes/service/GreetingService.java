package ke.co.technovation.stripes.service;

import java.util.List;

import ke.co.technovation.stripes.model.Greeting;

public interface GreetingService {
	
	public Greeting getHelloGreeting();
	public Greeting getGoodbyeGreeting();
	public Greeting saveOrUpdate(Greeting helloGreeting);
	public void init();
	public List<Greeting> listAll();

}
