@ECHO OFF

TITLE GATEWAY SERVICE
echo Starting gateway service....
:: To run the application on different port you can pass --server.port=9999 in below command
java -jar ./target/gateway-service-0.0.1-SNAPSHOT.jar

PAUSE
