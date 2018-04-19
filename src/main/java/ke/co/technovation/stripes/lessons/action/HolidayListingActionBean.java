package ke.co.technovation.stripes.lessons.action;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;


@UrlBinding("/holidayLookup.action")
public class HolidayListingActionBean implements ActionBean  {
	
	
	private String holiday;
	
	private ActionBeanContext context;

	public void setContext(ActionBeanContext context) {
		this.context = context;

	}

	public ActionBeanContext getContext() {
		return context;
	}
	
	@DefaultHandler
	public Resolution checkHoliday() {
		String holidayInfo = "please supply a holiday name for lookup. E.g christmas or easter";
		if(holiday!=null){
			if(holiday.equalsIgnoreCase("christmas")){
				holidayInfo = "Christmas is celebrated on the 25th Date of December.";
			}
			if(holiday.equalsIgnoreCase("easter")){
				holidayInfo = "Easter is celebrated from 1st April";
			}
		}
		return new StreamingResolution("text/html", holidayInfo);
	}
	
	

	public String getHoliday() {
		return holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}
	
	

}
