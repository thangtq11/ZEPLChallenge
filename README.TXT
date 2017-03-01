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
* data          H2 embedded database directory.
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
* go to ZEPLChallenge directory.
* running the todo service ( ip address and port of the service are configured in "config\application.properties" file.
 java -jar target/ZEPLChallenge-1.0.0.jar

* running the todo client for testing the service.
cd client
* TodoClient [ip address of the service] [port] [concurrent threads] [number of sessions: 1 (1 session = [11 requests + 11 responses]) ]
java -jar ZEPLChallenge-TodoClient-1.0.0.jar 127.0.0.1 8084 1 1

