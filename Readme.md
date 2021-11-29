# [Loyalty Program Award Calculation]

## Table of contents
1. [Softwares (versions) used](#softwares-used)  
2. [Features](#features)
3. [Schema and sample data](#schema-and-sample-data)
4. [How to run app](#how-to-run-app)
5. [End Points](#end-points)


## Softwares used 
- JDK 1.8.0_291
- H2 DB (In-memory database)
- Spring Boot v2.5.6
- Spring v5.3.12
- Lombok 1.8.22
- Hamcrest 1.3
- Google JMapper 1.6.1.CR2

## Features
- Loads sample data (customers, programs, program rules and transactions)
- Calculate award points for each customer
- Reports award points grouped by month and also aggregated total 

## Schema and sample data
#### schema.sql
```sql
DROP TABLE IF EXISTS customers;
CREATE TABLE customers (
	customer_id INT AUTO_INCREMENT PRIMARY KEY,
	first_name VARCHAR(250) NOT NULL,
	last_name VARCHAR(250) NOT NULL,
	email VARCHAR(200),
	create_date TIMESTAMP default CURRENT_TIMESTAMP,
	create_user VARCHAR(100) default 'Admin',
	update_date TIMESTAMP,
	update_user VARCHAR(100)
);

DROP TABLE IF EXISTS transactions;
CREATE TABLE transactions (
	transaction_id INT AUTO_INCREMENT PRIMARY KEY,
	customer_id INT NOT NULL,
	total DOUBLE,
	subtotal DOUBLE,
	tax DOUBLE,
	create_date TIMESTAMP default CURRENT_TIMESTAMP,
	create_user VARCHAR(100) default 'Admin',
	update_date TIMESTAMP,
	update_user VARCHAR(100),
	foreign key (customer_id) references customers(customer_id)
);

DROP TABLE IF EXISTS award_programs;
CREATE TABLE award_programs (
	program_id INT AUTO_INCREMENT PRIMARY KEY,
	program_name VARCHAR(250) NOT NULL,
	begin_date TIMESTAMP,
	end_date TIMESTAMP,
	create_date TIMESTAMP default CURRENT_TIMESTAMP,
	create_user VARCHAR(100) default 'Admin',
	update_date TIMESTAMP,
	update_user VARCHAR(100)
);

DROP TABLE IF EXISTS award_program_rules;
CREATE TABLE award_program_rules (
	rule_id INT AUTO_INCREMENT PRIMARY KEY,
	program_id INT,
	multiplier INT NOT NULL,
	minimum_amt DOUBLE,
	maximum_pts DOUBLE,
	tier INT,
	create_date TIMESTAMP default CURRENT_TIMESTAMP,
	create_user VARCHAR(100) default 'Admin',
	update_date TIMESTAMP,
	update_user VARCHAR(100)
);

DROP TABLE IF EXISTS award_program_activity;
CREATE TABLE award_program_activity (
	activity_id INT AUTO_INCREMENT PRIMARY KEY,
	program_id INT,
	customer_id INT,
	points_earned DOUBLE,
	create_date TIMESTAMP,
	create_user VARCHAR(100) default 'Admin',
	update_date TIMESTAMP,
	update_user VARCHAR(100)
);

DROP TABLE IF EXISTS customer_programs;
CREATE TABLE customer_programs(
	id INT AUTO_INCREMENT PRIMARY KEY,
	customer_id INT NOT NULL,
	program_id INT NOT NULL,
	create_date TIMESTAMP default CURRENT_TIMESTAMP,
	create_user VARCHAR(100) default 'Admin',
	update_date TIMESTAMP,
	update_user VARCHAR(100)
);
```
#### data.sql
```sql
INSERT INTO customers(first_name, last_name, email, create_date, create_user) VALUES ('Mark','Ruffalo','mark.ruffalo@example.com',CURRENT_TIMESTAMP,'Admin');
INSERT INTO customers(first_name, last_name, email, create_date, create_user) VALUES ('Scarlett','Johansson','scar.johan@example.com',CURRENT_TIMESTAMP,'Admin');
INSERT INTO customers(first_name, last_name, email, create_date, create_user) VALUES ('Robert','Jr','robert.jr@example.com',CURRENT_TIMESTAMP,'Admin');
INSERT INTO customers(first_name, last_name, email, create_date, create_user) VALUES ('Paul','Rudd','paul.rudd@example.com',CURRENT_TIMESTAMP,'Admin');
INSERT INTO customers(first_name, last_name, email, create_date, create_user) VALUES ('Josh','Brolin','josh.brolin@example.com',CURRENT_TIMESTAMP,'Admin');

INSERT INTO award_programs(program_name, begin_date, end_date) VALUES ('Buy more Get more','2021-10-10','2021-12-31');
INSERT INTO award_programs(program_name, begin_date) VALUES ('Future Loyalty plan','2022-10-10');

INSERT INTO customer_programs(customer_id, program_id) VALUES (1,1);
INSERT INTO customer_programs(customer_id, program_id) VALUES (1,2);
INSERT INTO customer_programs(customer_id, program_id) VALUES (2,1);
INSERT INTO customer_programs(customer_id, program_id) VALUES (3,1);

INSERT INTO award_program_rules(program_id, multiplier, minimum_amt, maximum_pts, tier) VALUES (1,1,'50','50',1);
INSERT INTO award_program_rules(program_id, multiplier, minimum_amt, maximum_pts, tier) VALUES (1,2,'100',-1,1);

INSERT INTO transactions(create_date,customer_id,subtotal,tax,total) VALUES ('2021-09-02 11:12:24',1,'49.99','3.75','53.74');
INSERT INTO transactions(create_date,customer_id,subtotal,tax,total) VALUES ('2021-09-05 11:12:24',1,'155.87','11.63','167.38');
INSERT INTO transactions(create_date,customer_id,subtotal,tax,total) VALUES ('2021-10-05 11:12:24',2,'110.85','11.63','167.38');
INSERT INTO transactions(create_date,customer_id,subtotal,tax,total) VALUES ('2021-11-05 11:12:24',2,'110.85','11.63','167.38');
INSERT INTO transactions(create_date,customer_id,subtotal,tax,total) VALUES ('2021-11-05 11:12:24',2,'80.85','11.63','167.38');
INSERT INTO transactions(create_date,customer_id,subtotal,tax,total) VALUES ('2021-09-01 09:12:24',2,'99.99','11.63','167.38');
INSERT INTO transactions(create_date,customer_id,subtotal,tax,total) VALUES ('2021-10-05 11:12:24',1,'90.87','11.63','167.38');
```

## How to run app
1. Clone the project from git ``` https://github.com/Ravi-Learner/loyalty-award-points.git ```
2. Build the project using ``` mvnw clean install ```
3. Run the project using ``` mvnw spring-boot:run ```
4. Application loads sample data using ``` data.sql ```
5. Tomcat is running on port ``` 8282 ```
6. H2 console is available at http://localhost:8282/loyalty/h2-console/
    **Jdbc url** - jdbc:h2:mem:awards
    **User** - sa
    **Password** - password
   
## End points
- GET http://localhost:8282/loyalty/customers/all  ``` (List all the customers)```
- GET http://localhost:8282/loyalty/customers/{customerId} ``` (Returns existing customer)```
- POST http://localhost:8282/loyalty/customers/create   ``` (Creates new customer)```
```  
        Request sample:
        {
            "first_name":"Ravi",
            "last_name":"Chhillar"
        }
```
- GET http://localhost:8282/loyalty/customers/awards/{customerId} ``` (Calculate and return awards of customerId) ```
- GET http://localhost:8282/loyalty/customers/awards/all ``` (Return all the awards in the system grouped by customer/month) ```