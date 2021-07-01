Promotion Engine Web Service Project

This is a Spring Boot web service implementation of the Promotion Engine

Build is accomplished with Gradle Build script with bootJar task used to create the Spring Boot JAR file. Once completed this can be found in the build/libs directory. This should be copied to a directory of your choice along with application.properties and PromotionRules.xml which can be found in the config directory. The server can then be executed with java -jar PromotionEngine.jar

By default in application.properties the server is listening on Port 9091 and the URL of the service is http://localhost:9091/order which requires a POST request with JSON data representing the Order to be checked. The response is also a JSON object with the total cost representing the lowest possible price based on the best promotion being applied to the order. 

Promotion Rules and SKU Prices can be adjusted by editing the PromotionRules.xml file to include new rules or SKUs. 
