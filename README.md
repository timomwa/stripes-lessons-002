# Stripes Framework : Lesson 001 - The basics

Welcome to the stripes framework course lesson 1.

This course is designed to get you from a beginner to a novice stripes framework developer in under one week. 

You shall learn by running and modifying examples in all the lessons.




## Objectives
By the end of this exercise, you will;

1. Be able to understand what the stripes framework is.
2. Understand stripe's configuration via web.xml
3. Understand how to use the `ActionResolver.Packages` parameter (in web.xml) for Action Beans auto discovery.
4. Understand the concept of `ActionBeans`
5. Understand Stripe's ActionBean default URL binding.
6. Be able to override the default Stripe's Action Bean URL binding by using the `@UrlBinding` annotation.


---


## Getting Started

To view this README.md better, use Eclipse IDE's "Preview" button at the bottom of the text editor.


---


## What is the *Stripes Framework* ?

According to the [stripes official website](https://stripesframework.atlassian.net/wiki/spaces/STRIPES/overview), 

>Stripes is a presentation framework for building web applications using the latest Java technologies.

This means that stripes helps you build the user interface of java web applications easily.

Feel free to read more about the framework, but you shall have plenty of time after going over this lesson.


---


### Prerequisites

Fulfill the following before getting running the examples.

* A decent internet connection (You shall need this to download maven dependencies)
* At least 100mb free disk space in your local machine or environment.
* JDK 1.8
* Eclipse IDE (one that runs on JDK 1.8)
* Maven 3.3.x


---

### Running this example.

* Navigate to the root of this folder and run the following commands
    
  `mvn clean package`

  `java -jar target/stripes-lessons-001-swarm.jar`
  
* Go to your browser and open the page

  `http://localhost:8981/stripes-lessons`
  
  
  
 
---
 
 
### What we learn from this example. 

Running this example combines a number of concepts;

1. Stripe's Configuration (maven dependencies, configuration via the web.xml)
2. Action Beans and their methods
3. web.xml configuration - ActionResolver.Packages
4. Stripe's Default URL binding
5. Overriding Stripe's Default URL binding.
6. Passing parameters to Action beans




#### Stripe's Configuration (maven dependencies, configuration via the web.xml)

Observe the pom.xml and note the maven dependencies, the key one being `stripes` ;

		<dependency>
			<groupId>net.sourceforge.stripes</groupId>
			<artifactId>stripes</artifactId>
			<version>1.5.8</version>
		</dependency>

In the deployment descriptor `src/main/webapp/WEB-INF/web.xml`, note the `StripesFilter` and `DispatcherServlet` 
which is all you need to configure stripes.


#### Action Beans and their methods

An ActionBean is the object that receives the data submitted in (HTTP) requests and processes the input.

Action Beans must implement the interface  `net.sourceforge.stripes.action.ActionBean`, and must provide 
getters and setters for `net.sourceforge.stripes.action.ActionBeanContext`.

`ActionBeanContext` provides access to the HttpServletRequest and HttpServletResponse for when you need them, 
as well as other information about the current request (e.g. error messages).

Stripes accesses values on ActionBeans through standard JavaBean getter and setter methods, and if they do not exist you will get errors.

#### web.xml configuration - ActionResolver.Packages

Notice lines 12-15 of web.xml. 

	12. <init-param>
	13. 	<param-name>ActionResolver.Packages</param-name>
	14. 	<param-value>ke.co.technovation.stripes.lessons.action</param-value>
	15. </init-param>

The parameter `ActionResolver.Packages` tells stripes the packaage in which to look for action beans.
We set our param value to `ke.co.technovation.stripes.lessons.action` because this is where our action bean
`HelloStripes` resides.

	
#### Stripe's Default URL binding

While the application is running, type the following URL on your browser;

`http://localhost:8981/stripes-lessons/HelloStripes.action`

You should see an output of the words "Hello World. Date - " plus the current date concatenated at the end. E.g

	Hello World. Date -Thu Apr 19 04:15:33 EAT 2018

To understand what is happening here, open the java class `ke.co.technovation.stripes.lessons.action.HelloStripes`

Take note of the code comments that are explaining what is happening.

Line 50-53 specifically looks like this;

	50:     @DefaultHandler
	51:     public Resolution sayHelloWorld() {
	52: 	     return new StreamingResolution("text/html", "Hello World. Date - "+(new Date()));
	53:     }

The annotation `@DefaultHandler` is the method that is executed by default when we call the action class, that is why
by default, we get the response "Hello World. Date -"...

If we wanted to call the method `sayGoodByeWorld` (line 60-62), below;

	60:     public Resolution sayGoodByeWorld() {
	61:     	return new StreamingResolution("text/html", "Goodbye World. Date - "+(new Date()));
	62:     }
	
We would us the URL - `http://localhost:8981/stripes-lessons/HelloStripes.action?sayGoodByeWorld` that should give the  words "Goodbye World. Date - " plus the current date concatenated at the end. E.g

	Goodbye World. Date -Thu Apr 19 04:15:33 EAT 2018

This is to show that by default, action bean URLs become *.action in our case, the HelloStripes had it's
URL bound to `HelloStripes.action`

#### Overriding Stripe's Default URL binding.
Sometimes, you want to give your action classes a custom URL, this is done via the @URLBinding 
annotation.

Open the class `ke.co.technovation.stripes.lessons.action.HolidayListingActionBean` 
observe line 11

	11:     @UrlBinding("/holidayLookup")

This tells stripes to overide the default URL binding and bind `/holidayLookup` as the action bean.

To call this class, type the following URL in the browser;

`http://localhost:8981/stripes-lessons/holidayLookup.action`


Observe that even though the class name is `HolidayListingActionBean` the @URLBinding annotation has
the string `"/holidayLookup"`  supplied, meaning that the Action bean's URL will be `"/holidayLookup"`.



#### Passing parameters

Now, in your browser we want to lookup two holiday's - "christmas" and "easter"

The lookup URLs for the two are as follows; Notice the URL query parameter namely `holiday`


`http://localhost:8981/stripes-lessons/holidayLookup.action?holiday=christmas`
`http://localhost:8981/stripes-lessons/holidayLookup.action?holiday=easter`

Notice that the two URLs above call the same Action bean, the only difference being the URL query parameter values.

Their outputs are also different.

Now look at the action bean that is doing all this magic.

Notice the action bean `ke.co.technovation.stripes.lessons.action.HolidayListingActionBean` has a private class variable 
of type String at line 15;

	15:     private String holiday;
	
The private class variable has getters and setters (line 44-50)


	44:     public String getHoliday() {
	45:     	return holiday;
	46:     }
	47:     
	48:     public void setHoliday(String holiday) {
	49:     	this.holiday = holiday;
	50:     }


The getters and setters are mandatory for stripe to take in query parameter values. The getters and setters must follow POJO getter and setter naming conventions, that is;

for the getter, the name `get` is prepended and the first letter of the class variable is changed to upper case.
The same happens for the setter, with `set` prepended and first letter of the class variable changed to upper case.

that is for the class variable `holiday`, the setter will be `setHoliday`, while it's getter, `getHoliday` 




---





#Exercise

Below are tasks that you must complete to demonstrate your understanding of stripes based on this lesson.



##Task 1

Modify the action bean `ke.co.technovation.stripes.lessons.action.HolidayListingActionBean` to service requests for the following holidays;

- Hanukkah
- New year's
- Mashujaa day
- System Administrator Appreciation Day

(These are actual holidays with dates. Google them and have the service return the actual dates)


##Task 2

* Create a new action bean called "Calculator", whose url is "summationService.action"
* The calculator must be able to accept two digits, add them together and return the result.


##Task 3

clean the project by typing `mvn clean`, zip the project and send to admin@technovation.co.ke for evaluation.


