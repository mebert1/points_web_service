# Points Web Service



This project implements a web service for point management. Points are tied to user accounts and associated to a company/partner.



**Developer**: Marius Ebert

**Language**: Java

**API**: REST

**Framework**: Spring







## Functionality

This service uses HTTP GET and POST requests to perform the following actions. 

- Create new user
- Check current account balance
- Add a transaction to a user account
- Make a payment with points from a user account
  - Uses the oldest points first
- See table of points per payer for a user account







## Build

After cloning the project from this git repository with

> ​	git clone git@github.com:mebert1/points_web_service.git

use

> ​	gradlew build

to build the project and

> ​	gradlew bootrun

to run. The web service should be available on http://localhost:8080.  See below for example usages of the service.



During the build, the project will run automated tests that can be found in src/test/java/web/.







## Example requests

Use these example requests in your preferred REST API testing tool or simply in your browser's address bar.



### Create new User:

**Type:** GET

**URL:** localhost:8080/newUser



### Request user point balance

**Type:** GET

**URL:** localhost:8080/balance?user=0



### Request list of points per payer:

**Type:** GET

**URL:** localhost:8080/payer_list?user=0



### Add Transaction

**Type:** POST

**URL:** localhost:8080/transaction/0

**Body:**

{
	"payer_name" : "DANNON",
	"points" : "300",
	"date" : "2021-30-01T17:08:48"
}



### Make payment

**Type:** GET

**URL:** localhost:8080/pay?amount=100&user=0

