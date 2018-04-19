package ke.co.technovation.stripes.lessons.action;

import java.util.Date;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;

/**
 * 
 * @author timothymwangi
 * 
 * This is a basic Action class that processes
 * requests.
 *
 */

public class HelloStripes implements ActionBean {
	
	private ActionBeanContext context;

	public void setContext(ActionBeanContext context) {
		this.context = context;

	}

	public ActionBeanContext getContext() {
		return context;
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
		return new StreamingResolution("text/html", "Hello World. Date - "+(new Date()));
	}
	
	/**
	 * This method does not have  @DefaultHandler annotation.
	 * You must explicitly specify that you want to call it from the URl.
	 * @return
	 */
	public Resolution sayGoodByeWorld() {
		return new StreamingResolution("text/html", "Goodbye World. Date - "+(new Date()));
	}

}
