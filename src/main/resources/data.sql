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