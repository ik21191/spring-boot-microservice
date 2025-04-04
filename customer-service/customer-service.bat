@ECHO OFF

TITLE CUSTOMER SERVICE
echo Starting customer service....
:: To run the application on different port you can pass --server.port=9999 in below command
java -jar ./target/customer-service-0.0.1-SNAPSHOT.jar

PAUSE
