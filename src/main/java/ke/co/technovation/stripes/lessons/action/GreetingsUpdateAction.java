package ke.co.technovation.stripes.lessons.action;

import java.util.List;

import org.apache.log4j.Logger;

import ke.co.technovation.stripes.model.Greeting;
import ke.co.technovation.stripes.service.GreetingService;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.integration.spring.SpringBean;

public class GreetingsUpdateAction  extends BaseActionBean {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@SpringBean
	private GreetingService greetingService;
	
	private Greeting greeting;

	public Greeting getGreeting() {
		return greeting;
	}

	public void setGreeting(Greeting greeting) {
		this.greeting = greeting;
	}
	
	
	@HandlesEvent("new")
	public Resolution createNew(){
		String response = "";
		try{
			greeting  = greetingService.saveOrUpdate(greeting);
			response = "New greeting with the id ["+greeting.getId()+"] was created!";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			response = "There was an error creating new greeting : "+e.getMessage();
		}
		
		return new StreamingResolution("text/html", response);
	}
	
	@HandlesEvent("init")
	public Resolution initialize(){
		
		String response = "";
		try{
			greetingService.init();
			response = "Initialized Successfully!";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return new StreamingResolution("text/html", response);
		
	}
	
	
	
	@HandlesEvent("listAll")
	public Resolution listGreettings(){
		
		String response = "";
		
		try{
			
			
			List<Greeting> greetings = greetingService.listAll();
			
			if(greetings!=null && !greetings.isEmpty()){
				response += "<html>";
				response += "<head>";
				response += getStyling();
				response += "<head>";
				response += "</head>";
				response += "<body>";
				response += "<table id=\"greetingsTable\">";
				response += "<tr><th>ID</th> <th>Type</th> <th>Language</th><th>Greeting</th>  </tr>";
				
				for(Greeting greeting : greetings){
					response += "<tr><td>"+greeting.getId()+"</td> <td>"+greeting.getType()+"</td> <td>"+greeting.getLanguage()+"</td> <td>"+greeting.getGreeting()+"</td></tr>";
				}
				
				response += "</table>";
				response += "</body>";
				response += "</html>";
				
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return new StreamingResolution("text/html", response);
	}

	private String getStyling() {
		String styling = "<style>"
		+" #greetingsTable {"
		+"    font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;"
		+"    border-collapse: collapse;"
		+"     width: 60%;"
		+" }"

		+" #greetingsTable td, #customers th {"
		+"    border: 1px solid #ddd;"
		+"    padding: 8px;"
		+" } "

		+" #greetingsTable tr:nth-child(even){background-color: #f2f2f2;}"

		+" #greetingsTable tr:hover {background-color: #ddd;}"
		
		+" #greetingsTable th {"
		+"    padding-top: 12px;"
		+"    padding-bottom: 12px;"
		+"    text-align: left;"
		+"    background-color: #4CAF50;"
		+"    color: white;"
		+"} "
		+" </style>";
		
		return styling;
		
	}
	
	

}
