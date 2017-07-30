# FANS Backend Challenge
FANS Backend Challenge with Spring Boot Maven project.

## Overview
The purpose of this application is to monitor a server and log its state.

It was deployed on Heroku and it is built automatically on each commit.
It also contains a simple UI with a table to display reports.

The application is available on https://murmuring-castle-26388.herokuapp.com

## Features
* Monitoring endpoints
   * Start: https://murmuring-castle-26388.herokuapp.com/start
   * Stop: https://murmuring-castle-26388.herokuapp.com/stop
   * Status: https://murmuring-castle-26388.herokuapp.com/status
   * Clear: https://murmuring-castle-26388.herokuapp.com/clear

## How to build & run
To build the application, clone the project from github, navigate to the project folder and type:
```
mvn clean install
```

To run, navigate to ./target folder and type:
```
java -jar fans-backend-challenge-0.0.1-SNAPSHOT.jar
```