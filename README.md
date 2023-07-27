# Getting Started

## Software
* Java 8
* Spring Boot 2.7


## API Documentations

* [Postman Documentations](https://documenter.getpostman.com/view/5858223/2s946pYozn)
* Swagger (http://localhost:8080/swagger-ui.html)


## Database Structure
The program consists of two tables as follows:

### Grade
This table has the following columns:

Column | Definition
--- |------------|
id | integer, PK
name | varchar
bonus_percentage | integer, nullable

### Employee
This table has the following columns:

Column | Definition
--- |------------|
id | long, PK
name | varchar
salary | long
grade_id | integer, FK