package ke.co.technovation.stripes.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import ke.co.technovation.stripes.model.Greeting;

public class GreetingDAOImpl implements GreetingDAOI { 

	private Logger logger = Logger.getLogger(getClass());
	
	@PersistenceContext
	private EntityManager em;
	
	

	public Greeting getGreeting(String type) {
		
		Greeting greeting = null;
		
		try{
			Query qry = em.createQuery("FROM ke.co.technovation.stripes.model.Greeting g where g.type = :type ");
			qry.setParameter("type", type);
			qry.setMaxResults(1);
			greeting = (Greeting) qry.getSingleResult();
		}catch(NoResultException nre){
			logger.warn(" Could not find a record of ke.co.technovation.stripes.model.Greeting of type "+type+" does it really exist in the db?");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return greeting;
	}


	public Greeting saveOrUpdate(Greeting greeting) {
		
		try{
			greeting = em.merge(greeting);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return greeting;
	}


	@SuppressWarnings("unchecked")
	public List<Greeting> listAll() {

		List<Greeting> greetings = null;
		
		try{
			Query qry = em.createQuery("FROM ke.co.technovation.stripes.model.Greeting ");
			greetings = qry.getResultList();
		}catch(NoResultException nre){
			logger.warn(" No greetings found! ");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return greetings;
	}

}
