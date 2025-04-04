@ECHO OFF

echo Starting microservices.......
:: Starting discovery service
cd discovery-service
start cmd /k "discovery-service.bat"

:: Starting gateway-service
:: Pausing for 30 sec
timeout /t 30
cd ..
cd gateway-service
start cmd /k "gateway-service.bat"

::Starting customer-service
timeout /t 30
cd ..
cd customer-service
start cmd /k "customer-service.bat"

::Starting order service
timeout /t 30
cd ..
cd order-service
start cmd /k "order-service.bat"

::echo microservices started.

::PAUSE