package ke.co.technovation.stripes.dao;

import java.util.List;

import ke.co.technovation.stripes.model.Greeting;


public interface GreetingDAOI {
	
	public Greeting getGreeting(String type);

	public Greeting saveOrUpdate(Greeting helloGreeting);

	public List<Greeting> listAll();

}
