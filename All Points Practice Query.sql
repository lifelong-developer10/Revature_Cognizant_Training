-- A. Table Creation (Basics)

use orders;

-- Create a table employeees with columns: employee_id, first_name, last_name, email, phone_number, hire_date.
create table employeees (employee_id int ,first_name varchar(10),last_name varchar(10), email varchar(20),
phone_number bigint,hire_date date);

-- Create a table departments with columns: department_id, department_name, location_id.
create table departments (department_id int,department_name varchar(20),location_id bigint);

-- Create a table jobs with columns:job_id, job_title, min_salary, max_salary.
create table jobs (job_id int,job_title varchar(10),min_salary bigint,max_salary bigint);

-- Create a table locations with columns:location_id, street_address, city, state_province, country_id.
create table locations (location_id bigint,street_address varchar(20),city varchar(10),state_province varchar(10),country_id bigint);

-- Create a table countries with columns: country_id, country_name, region_id.
create table countries (country_id bigint,country_name varchar(20),region_id bigint);


-- B. Constraints Practice

-- Add a PRIMARY KEY to employees(employee_id).
ALTER TABLE employeees ADD constraint employee_id primary key(employee_id);

-- Add a UNIQUE constraint on employees(email).
alter table employeees add constraint unique(email);
desc employeees;

-- Add a NOT NULL constraint on employees(last_name).
alter table employeees modify  last_name varchar(10) not null;

-- Add a PRIMARY KEY to departments(department_id).
ALTER TABLE departments ADD CONSTRAINT PRIMARY KEY(department_id);

-- Add a FOREIGN KEY on employees(department_id) referencing departments(department_id).
ALTER TABLE departments ADD CONSTRAINT department_id foreign key(department_id) references employeees(department_id); 


-- C. Advanced Table Creation

 -- Create table job_history with: employee_id, start_date, end date, job_id, department_id and set a composite primary key on (employee_id, start_date).
create table job_history(employee_id int, start_date date, end_date date, job_id int, department_id int , primary key(employee_id,start_date));

-- Create table regions with region_id as PRIMARY KEY and region_name as UNIQUE.
create table regions (region_id bigint primary key, region_name varchar(20) unique);

-- Create table dependents with:dependent_id (PK), first_name, last_name, relationship, employee_id (FK).
create table dependents (ependent_id int primary key, first_name varchar(10), last_name varchar(10), relationship varchar(10), 
employee_id int, foreign key(employee_id) references employeees(employee_id) );

-- Create table projects with:project_id (PK), project_name, start_date, end_date.
create table projects (project_id int primary key,project_name varchar(20),start_date date,end_date date);

-- Create table employee_projects with composite PK(employee_id, project_id).
create table employee_projects (employee_id int,project_id int , primary key(employee_id,project_id));


-- D. ALTER TABLE Practice

-- Add column salary to employees.
ALTER TABLE employeees ADD COLUMN salary bigint;

-- Add column commission_pct to employees.
ALTER TABLE employeees ADD COLUMN comission_pct bigint;

-- Modify column salary to DECIMAL(10,2).
ALTER TABLE employeees MODIFY COLUMN salary decimal(10,2);

-- Rename column phone_number to mobile_number in employees.
ALTER TABLE employeees RENAME COLUMN phone_number TO mobile_number;

-- Drop column commission_pct from employees.
ALTER TABLE employeees DROP COLUMN comission_pct;

-- E. Indexes

-- Create an INDEX on employees(last_name).
CREATE INDEX emplastname ON employeees(last_name);

-- Create a UNIQUE INDEX on departments(department_name).
CREATE UNIQUE INDEX depname ON departments(department_name);

-- Drop the index created on employees(last_name).
ALTER TABLE employeees DROP INDEX emplastname;


-- F. Table & Schema Management

-- Rename table employees to staff.
ALTER TABLE employeees RENAME TO staff;

-- Truncate table job_history.
truncate table job_history;

-- Drop table employee_projects.
Drop table employee_projects;


-- G. Constraints Modification


-- Drop the foreign key constraint between employees and departments.
ALTER TABLE departments DROP FOREIGN KEY department_id ;

-- Add a foreign key between job_history(job_id) and jobs(job_id).
ALTER TABLE job_history ADD CONSTRAINT FOREIGN KEY(job_id) REFERENCES jobs(job_id);

-- Add ON DELETE CASCADE to the foreign key between dependents(employee_id) and alter table orders add constraint order_cid foreign key(order_cid) references customers(customer_id) on delete cascade;.
alter table dependents add constraint foreign key(emplyoee_id) references customers on delete cascade;

-- Add a CHECK constraint on employees(salary) to ensure salary > 0.
use orders;
show tables;
ALTER TABLE staff ADD CONSTRAINT CHECK  (salary > 0);