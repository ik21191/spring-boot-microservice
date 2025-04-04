@ECHO OFF

TITLE ORDER SERVICE
echo Starting order service....
:: To run the application on different port you can pass --server.port=9999 in below command
java -jar ./target/order-service-0.0.1-SNAPSHOT.jar

PAUSE
