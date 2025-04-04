@ECHO OFF

TITLE DISCOVERY SERVICE
echo Starting discovery service....
:: To run the application on different port you can pass --server.port=9999 in below command
java -jar ./target/discovery-service-0.0.1-SNAPSHOT.jar

PAUSE
