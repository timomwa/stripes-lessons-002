# Stripes Framework : Lesson 002 - Integration with Spring and JPA

Welcome to the stripes framework course lesson 2.

This course is designed to get you from a beginner to a novice stripes framework developer in under one week. 

You shall learn by running and modifying examples in all the lessons. Unless specified, you do not need to read any other matereal.




## Objectives
By the end of this exercise, you will;

1. Understand the workings of JPA and how JPA layer talks to databases
2. Understand JPA configuration & Data sources (persistence.xml)
3. Understand what JPA Entities are and map them to database tables.
4. Understand JPA Query Language (QL) and JPA's Entity Manager.
       Be able to read from the database via JPA
       Be able to write data into the database via JPA
5. Understand DAOs (Data Access Objects)
6. Understand how to pass/create objects via stripes   



---


## Getting Started

To view this README.md better, use Eclipse IDE's "Preview" button at the bottom of the text editor.


---


### Prerequisites

Fulfill the following before getting running the examples.

* A decent internet connection (You shall need this to download maven dependencies)
* At least 100mb free disk space in your local machine or environment.
* JDK 1.8
* Eclipse IDE (one that runs on JDK 1.8)
* Maven 3.3.x
* Running mysql/Maria db database. (This example was tested on Version 5.7.18 on Mac OSX)
* A mysql user with admin rights.




---


## What is *JPA* ?
JPA stands for *Java Persistence API - API being Application Programming Interface -*.

It is a Java specification for accessing, persisting, and managing data between Java objects / classes and a relational database and of late non relational databases such as NoSQL.

This simply means that JPA will allow you to focus on the modeling of your business logic
and abstract you from the logic of talking to relational and non-relational databases.

Java is improved by what the java community calls JSR (Java Specification Requests). This is a common dossier (document)
that details what goes into the java platform. Anyone can make a Java specification request, e.g ask the community to include a certain function, e.g native JSON handlers into a Java version's release. 

When you have time, [read about JSR HERE](https://www.jcp.org/en/jsr/overview).

### JPA Implementations
Since JPA is an API, there exists other layers that implement JPA and make things a little simple for us.
In this case, we shall use hibernate. After this exercise, you can [browse through hibernate's web page](http://hibernate.org/) for a feel of what hibernate is capable of doing.

Other JPA implementations include, but not limited to;
- Toplink
- EclipseLink
- Apache OpenJPA

You might never need to use all, hibernate is the most popular of them all. Hibernate is considered the most advanced too.

### What is ORM
ORM stands for Object Relational Mapping. This is where you map a Java object
to a database table using JPA annotations.


---

### Running this example.

* Log into your mysql database with your admin user.
* Create a database called `stripes` by typing the following;

  `mysql> create schema stripes;`
  
  Upon success, you should see the output below;
  
  `Query OK, 1 row affected (0.00 sec)`
  
* Open the wildfly swarm's data source file `/stripes-lessons-002/src/main/resources/project-stages.yaml`

  lines 11-13 look like this;
  
		 9:  StripesDS:
		10:  driver-name: com.mysql
		11:    connection-url: jdbc:mysql://localhost:3306/stripes
		12:    user-name: root
		13:    password: 

  Take note of line #9. This is the name we give our data source `StripesDS`. We shall use it in our other configuration file.
  
  Modify these lines to reflect the connection url, db user and password of your mysql server and save the file.
  

* Navigate to the root of this folder and run the following commands
    
  `mvn clean package`

  `java -jar target/stripes-lessons-002-swarm.jar`
  
* Go to your browser and open the page

  `http://localhost:8981/stripes-lessons`
  
  This opens the overview of this course.
  
* This example advances from the first lesson in that we are now storing and retrieving the greetings from the database via JPA. To initialize and save some data into the db, run the url below on your browser;

  `http://localhost:8981/stripes-lessons/GreetingsUpdate.action?init`
  
  If successful, you should seee the response below;
  
  `Initialized Successfully!`

  This means that some initialization data has been stored into the db;
  
  Now, let's list all greetings available by running the url below;
  
  `http://localhost:8981/stripes-lessons/GreetingsUpdate.action?listAll`
  
  You should see a fancy table of all greetings available in the db.
  
  At this point, you can do an sql into the database we created called `stripes`
  There is a table called `greetings`. Take note of this as the next sections 
  will answer how this came to be.
  
  
  Now, let's create a new swahili greeting. Run the following URL in your browser (notice that there are no spaces in the URL);
  
  `http://localhost:8981/stripes-lessons/GreetingsUpdate.action?new&greeting.greeting=Habari&greeting.type=hello&greeting.language=sw_KE`
  
  You should see the response below;
  
  `New greeting with the id [#] was created!`
  
  Where # should be a number corresponding to the ID of the item created.
  
  Now, let's list all the greetings again and see whether it is true there is a new greeting added;
  
  You should now see 3 rows if the addition was successful.
 
  
 
---
 
 
### What we learn from this example. 

#### Understand the workings of JPA and how JPA layer talks to databases.

You have just used JPA to create and list greetings via stripes Action classes.
JPA requires what we call a data source. This helps us talk to the database and in our
case, the configuration file is `/stripes-lessons-002/src/main/resources/project-stages.yaml`

There are many ways to configure data sources, for our case, this is how you create a
data source in wildfly-swarm. (more on this later). For now we need to understand how JPA is used
with stripes.

#### Understand JPA configuration & Data sources (persistence.xml)

Once you have your datasource, we need to tell JPA to use the particular datasource
plus many other instructions that we shall see in this example.

Open the file `stripes-lessons-002/src/main/resources/META-INF/persistence.xml`

The file has 16 lines as follows;


		1: <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		2:  <persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="2.1" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
		3:   <persistence-unit name="stripesPU" transaction-type="RESOURCE_LOCAL">
		4:     <description>Forge Persistence Unit</description>
		5:     <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
		6:     <jta-data-source>java:jboss/datasources/StripesDS</jta-data-source>
		7:     <mapping-file>META-INF/orm.xml</mapping-file>
		8:     <exclude-unlisted-classes>false</exclude-unlisted-classes>
		9:     <properties>
	   10:        <property name="hibernate.hbm2ddl.auto" value="update"/>
	   11:        <property name="hibernate.show_sql" value="false"/>
	   12:        <property name="hibernate.format_sql" value="false"/>
	   13:        <property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect"/>
	   14:     </properties>
	   15:   </persistence-unit>
	   16: </persistence>

Pay special attention to line's 6 and 13.

Line 6 tells JPA to use the data source namely `java:jboss/datasources/StripesDS`, the suffix `java:jboss/datasources/` is what swarm gives our data source and together, the reference is called a [JNDI](https://en.wikipedia.org/wiki/Java_Naming_and_Directory_Interface) link. You can read more about JNDI when there is time.

For now, think of it as a link that wildfly-swarm creates for us to be able to access the datasource.


Pay attention when wildfly starts. When a data source is created, you will see in the wildfly startup log a like like this;


	`WFLYJCA0001: Bound data source [java:jboss/datasources/StripesDS]`
	
Being able to determine the JNDI names is very important when you're working with data sources.	

Line 6 tells JPA that the database being interfaced to is a MySQL database, hence the value of `hibernate.dialect` is set to `org.hibernate.dialect.MySQLDialect`. If we were talking to a different database, say Oracle, we would set this value to the respective dialect.



#### Understand what JPA Entities are and map them to database tables.

JPA Entities are just java objects with the following characteristics.

1. Private class variables (primitive types or other entities)
2. Getter and setters for each of the methods.
3. Getter methods or class variables are annotated by `@javax.persistence.Column`
4. One method or class variable as an ID (or unique identifier), annotated with `@javax.persistence.Id` and in our example `javax.persistence.GeneratedValue` so that ids are generated automatically.
5. At class level, the annotation `javax.persistence.Entity` is used to mark the class as a JPA entity.
6. At class level, the annotation `javax.persistence.Table` with the parameter `name` assigned to the database table name.


In our example, we only have one JPA entity - `ke.co.technovation.stripes.model.Greeting`

Have a look at it and notice all the characteristics mentioned above.





#### Understand JPA Query Language (QL) and JPA's Entity Manager.

JPA Query language (JQL) is very close to SQL. One major difference is that JPA query language is not
specific to any RDBMS. This means that if you write JPA query language, it will not matter whether your
datasource connects to a MySQL, Oracle, PostgreSQL etc. JPA Query language is portable and independent 
of the underlying data source.

In our example, we could modify the data source and dialect to use an Oracle database, and the JPA Query language will work without further modification.

After this exercise, you can [read More](https://docs.oracle.com/javaee/6/tutorial/doc/bnbtg.html) about JPA Query language.

An Entity Manager is the resource that allows you to write and call the database via JPA Query language. In our case, look at the class `ke.co.technovation.stripes.dao.GreetingDAOImpl`

Observe lines 18-19;

	18:    @PersistenceContext
	18:    private EntityManager em;


The annotation `javax.persistence.PersistenceContext` is placed at JPA's entity manager  `javax.persistence.EntityManager` interface to tell JPA to inject the entity manager.


##### Reading from the database via JPA
In the same class (`ke.co.technovation.stripes.dao.GreetingDAOImpl`), we can see how the entity manager is used to form JPA Queries, pass parameters and return results.

		28: Query qry = em.createQuery("FROM ke.co.technovation.stripes.model.Greeting g where g.type = :type ");
		.
		.
		.
		60: Query qry = em.createQuery("FROM ke.co.technovation.stripes.model.Greeting ");


##### Writing to a database via JPA

In the same class - `ke.co.technovation.stripes.dao.GreetingDAOImpl` we can see how simple it is to write into a database via JPA. Observe line 45 ;


	greeting = em.merge(greeting);
	


#### Understand DAOs (Data Access Objects)

Data Access Objects are re-useable beans that we store methods for CRUD operations.
`ke.co.technovation.stripes.dao.GreetingDAOImpl` is our example's DAO.

Notice how the DAO is called.

1. From the Stripe's Action bean - `ke.co.technovation.stripes.lessons.action.GreetingsActionBean`
2. `GreetingsActionBean` has a `GreetingService` which is a Spring Bean. And ... inside this service, 
3. The DAO is injected by spring's `@Autowired` annotation.

		@Autowired
		protected GreetingDAOI greetingDAOI;



#### Understand how to pass/create objects via stripes
In our example, we created a new object of type `ke.co.technovation.stripes.model.Greeting`.
The URL of this action was;

		http://localhost:8981/stripes-lessons/GreetingsUpdate.action?new&greeting.greeting=Habari&greeting.type=hello&greeting.language=sw_KE
		
  If you remember how to pass parameters from the first example namely [Stripes Framework : Lesson 001 - The Basics](https://github.com/timomwa/stripes-lessons-001), we pass values to Action Bean class variables with getters and setters by specifying query parameters with parameter names that match those in the bean.
  
  In this example, this has been taken a step further. Notice now that in our URL, we have 3 parameters as follows;
  
  
  
  
|Parameter Name      | Parameter Value   |

| -----------------:   | :---------------: |

| greeting.greeting| Habari			  |

| greeting.type     | hello			  |

| greeting.language | sw_KE			  |



---





# Exercise

Below are tasks that you must complete to demonstrate your understanding of stripes based on this lesson.

# Task 1
By following parameter passing example and without modifying this example, form 3 other URLs for creating new other greetings of type hello and the following languages;

- French
- Italian
- Kikuyu

update the section "Excercie README" and list these new URLs in this section below the Task Number as a header.

# Task 2
a) Modify the `ke.co.technovation.stripes.model.Greeting` entity so that it accepts another String value "createdBy". The matching database column should be called "created_by". Restart the application so that the column is created. 
b) Modify the URL's in task 1 to accept the createdBy parameter of the Entity  `ke.co.technovation.stripes.model.Greeting` and update the section "Exercise README" below with instructions on how to call the URL (you can just paste the new URLs there).



## Task 3
Create a new entity namely `Holiday` with the following properties;

- id - type java.lang.Long
- name - java.lang.String
- dateCelebrated - java.util.Date

Create appropriate standard getter and setter methods.

You will need to do a little bit of reasearch on how to deal with JPA dates.

Make sure this entity class is in the same package as the `Greeting` entity.


## Task 4
Create a DAO for the entity created in task #3. Call it `HolidayDAOI` for the interface, and `HolidayDAOImpl` for the implementation. Make sure they're in the same package as the other DAOs. Make sure the DAO has similar methods to `GreetingDAOI` and implementations. The only difference they'll be DAOs for the `Holiday` entity.

##Task 5
Create an action class called `HolidayActionBean` in the same fashion `ke.co.technovation.stripes.lessons.action.GreetingsActionBean` was created. Take note of the class being extended by `GreetingsActionBean`.

## Task 6
Create a Spring Bean called `HolidayService` in the same fashion `ke.co.technovation.stripes.service.GreetingService` was created, and inject it's 

## Task 7
Modify the `HolidayActionBean` you created in Task #5 and;

1. Inject the `HolidayService` into `HolidayActionBean` in the same fashion `Greeting`


## Task 8
Modify `HolidayService` you created in Task 6 and inject the `HolidayDAOI`  in the same fashion `GreetingDAOI` is injected into `GreetingService`

## Task 8
In `HolidayActionBean` create handlers for the following actions;
- listAll
- new

Update instructions on how to invoke the two new methods via browser in the "Exercise README" below.

clean the project by typing `mvn clean`, zip the project and send to admin@technovation.co.ke for evaluation.



#Exercice README


