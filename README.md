# Task Scheduling for Smart Homes

This project has originated from the "Advanced Services Engineering" course at TU Wien.

We would like to thank Prof. Hong-Linh Truong for his support and the opportunity to work on such an interesting project.

Authors: Fabian Guschlbauer, Bernhard Knasmueller

## Project Idea

In future smart homes, there are several household tasks which do not have to be run at a specific point in time but can be scheduled in a flexible way. If a photovoltaic panel is installed that generates electricity, one of course wants to run such tasks when most power is available.

Our project tackles this issue and provides a scheduling system that takes as inputs several parameters like the current power output, the current weather and the list of waiting tasks and returns a schedule that aims to minimize the amount of energy that has to be bought from additional external sources.

## Technical Overview

This project consists of 4 Java-based services (Analysis, EnergyPrice, EnergyProduction and WeatherData) that are run in individual docker containers. A fifth project named Shared contains shared classes and constants.

To interact with the software, there is a web-based GUI available via the analysis service (based on AngularJS).

## Getting Started

Before you can run this application you have to perform some additional configurations:
1) Add your Amazon credentials to the "credentials" file in your home/.aws folder.
`[
default]
aws_access_key_id=TODO
aws_secret_access_key=TODO`

2) Add your AWS RDS database credentials to "AnalysisService\src\main\java\com\ase\iec\AnalysisApplication.java".

`@SpringBootApplication
//enable this to use AWS RDS
@EnableRdsInstance(username="TODO", databaseName="TODO", password="TODO", dbInstanceIdentifier="TODO")
public class AnalysisApplication {`

3) You can also use your own local mysql database. To do so, outcomment the following line:

`@EnableRdsInstance(username="TODO", databaseName="TODO", password="TODO", dbInstanceIdentifier="TODO")`

and add your mysql credentials to "AnalysisService\src\main\resources\application.properties"

4) Set your OpenWeatherMap API key in "WeatherDataService\src\main\java\com\ase\iec\util\WeatherUtil.java"

5) In NotificationService.java, enter your Amazon SNS Topic-ARN:

`PublishRequest publishRequest = new PublishRequest("TODO INSERT TOPIC ARN", text);`


In order to run all individual services in docker containers, follow these instructions:

1) Install docker
2) Depending on your system configuration you may need to use "sudo" for all the following commands
3) Run "sudo mvn clean install" inside the "Shared" project's folder
4) Run "sudo mvn clean package docker:build" inside all other project folders
5) After a successful build you can run "sudo docker images" to list all built docker images
6) This should look like:

CREATED             SIZE
energypriceservice/energypriceservice             latest              619ef127287b        24 seconds ago      222.1 MB
weatherdataservice/weatherdataservice             latest              95bed8a9eb67        3 minutes ago       220.9 MB
energyproductionservice/energyproductionservice   latest              09e70bb33a05        3 minutes ago       222.1 MB
analysis/analysis                                 latest              d7455793f4

7) Now you can start each service using commands like "sudo docker run --rm --net=host -t energypriceservice/energypriceservice"


