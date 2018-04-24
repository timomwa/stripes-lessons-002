package ke.co.technovation.stripes.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ke.co.technovation.stripes.dao.GreetingDAOI;
import ke.co.technovation.stripes.lessons.action.InitializationClass;
import ke.co.technovation.stripes.model.Greeting;
import ke.co.technovation.stripes.service.GreetingService;

@Service
@Transactional
public class GreetingServiceImpl implements GreetingService{
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	protected GreetingDAOI greetingDAOI;
	
	
	public Greeting getHelloGreeting() {
		try{
			return greetingDAOI.getGreeting("hello");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public Greeting getGoodbyeGreeting() {
		try{
			return greetingDAOI.getGreeting("goodbye");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public Greeting saveOrUpdate(Greeting helloGreeting) {
		
		try{
			helloGreeting =  greetingDAOI.saveOrUpdate(helloGreeting);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return helloGreeting;
		
	}

	public void init() {
		
		Greeting helloGreeting  = getHelloGreeting();
		if(helloGreeting==null){
			helloGreeting = InitializationClass.createHello();
			helloGreeting = saveOrUpdate(helloGreeting);
		}
		
		Greeting goodByeGreeting  = getGoodbyeGreeting();
		if(goodByeGreeting==null){
			goodByeGreeting = InitializationClass.createGoodbye();
			goodByeGreeting = saveOrUpdate(goodByeGreeting);
		}
	}

	public List<Greeting> listAll() {
		
		return greetingDAOI.listAll();
	
	}

}
