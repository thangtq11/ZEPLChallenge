========================================================================
README.TXT
========================================================================
*** directory structures *****************************
* src			Application/Library sources.
* config		Configuration files. For exp: changing port of the todo service
* target		The target directory is used to store all output of the build with mvn command.
* log			storing log files - for log4j.
* doc			documents of the challenge project: api document.
* pcap			captured files for debugging communication between todo client and the todo service. open with wireshark.
* client		A Client for testing the todo service.
* pom.xml		for building maven project.
* todo-challenge.md	Requirements for building project.
* README.TXT		Project's readme
========================================================================
Technology
========================================================================
1. Java 8 (lambda, stream ).
2. Spring boot framework.
2. Guice for Dependency Injection. Building ToDoApplication.
3. Jetty & JAX-RS for web framework. 
4. Jersey for Restful framework.
5. Jackson for JSON serialization/deserialization.
6. Spring persistence: H2 embedded database.
7. Log4j for logging.
8. Maven for building the project.
========================================================================
Install and Run
========================================================================

1. INSTALL
* making the project with running tests 
 mvn install

* making the project without running tests 
 mvn install -Dmaven.test.skip=true

2. RUNNING
* running the todo service ( ip address and port of the service are configured in "config\application.properties" file.
 java -jar ZEPLChallenge-1.0.0.jar 

* running the todo client for testing the service.
cd client
java -jar ZEPLChallenge-TodoClient-1.0.0.jar 10.30.164.132 8084 1 1

