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