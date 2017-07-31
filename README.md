# FANS Backend Challenge
FANS Backend Challenge with Spring Boot Maven project.

## Overview
The purpose of this application is to monitor a server and report its state.

It was deployed on Heroku and is being built automatically on each commit. It also contains a simple UI to display reports.

The application is available on https://murmuring-castle-26388.herokuapp.com

## Features
* Monitoring endpoints
   * Start: https://murmuring-castle-26388.herokuapp.com/start
   * Stop: https://murmuring-castle-26388.herokuapp.com/stop
   * Status: https://murmuring-castle-26388.herokuapp.com/status
   * Clear: https://murmuring-castle-26388.herokuapp.com/clear

By default, the following values will be used if none was informed:
- **Interval:** 3000 ms
- **Hostname:** https://api.test.paysafe.com/accountmanagement/monitor

## How to build & run
To build the application, clone the project from github, navigate to the project folder and type:
```
mvn clean install
```

To run, navigate to ./target folder and type:
```
java -jar fans-backend-challenge-0.0.1-SNAPSHOT.jar
``` 