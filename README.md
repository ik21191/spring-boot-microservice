# spring-boot-microservice

- java17 is the compatible version for this application.

- You can run `run.bat` file to launch all application

- If you are going to run these services manually then follow below sequence


* discovery-service
* gateway-service
* customer-service
* order-service

-- You can check the load balancing by running the same application multiple times using different port using `--server.port=port_number`. 

For example you can create multiple instances of `customer-service` and  `order-service`.