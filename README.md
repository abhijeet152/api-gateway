# spring-gateway
Gateway will intiall run on port 8080 so to make request use link `http://localhost:8080/{serviceName}/**` <br/>
Here `servicename` is various qrebl-services followed by the url of respective service . <br/>
## Club Service
* Port - 9000
* Service Name - clubs

## Panel 1 Service
* Port - 9100
* Service Name - panels1

## Match Upload Service
* Port - 9400
* Service Name - match-upload

> To view wheather the services are registered to got `http://localhost:8761` after starting eureka. The port of eureka is 8761

**Note :** To run any request please activate eureka first then all other services including gateway
