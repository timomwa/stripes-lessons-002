package ke.co.technovation.stripes.lessons.action;

import ke.co.technovation.stripes.model.Greeting;

public class InitializationClass {

	public static Greeting createHello() {
		Greeting greeting = new Greeting();
		greeting.setGreeting("Hello World!");
		greeting.setLanguage("en_US");
		greeting.setType("hello");
		return greeting;
	}

	public static Greeting createGoodbye() {
		Greeting greeting = new Greeting();
		greeting.setGreeting("Goodbye World!");
		greeting.setLanguage("en_US");
		greeting.setType("goodbye");
		return greeting;
	}

}
