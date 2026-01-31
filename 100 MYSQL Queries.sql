-- 100 Queries For Prcatice

-- 1. Create a database named sales_db.
create database sales_db;
use sales_db;

-- 2. Create a table customers with columns: customer_id, name, email, phone.
create table customers (customer_id int,name varchar(20),email varchar(20),phone bigint);

--  3. Add a PRIMARY KEY to customer_id.
alter table customers add constraint  primary key(customer_id);

-- 4. Add a UNIQUE constraint to email.
alter table customers add constraint unique (email);

-- 5. Add a column created_at with DEFAULT CURRENT_TIMESTAMP.
ALTER TABLE customers add column created_at datetime;

-- 6. Create a table orders with FOREIGN KEY referencing customers.
create table orders (order_id int, amount bigint,ordertime time ,order_cid int, foreign key(order_cid) references customers(customer_id));

-- 7. Create a table employees with CHECK constraint: salary > 0.
create table employees (emp_id int, emp_department varchar(20), salary bigint,check (salary > 0));

-- 8. Add AUTO_INCREMENT to customer_id.
  alter table customers  modify column  customer_id int AUTO_INCREMENT ;

-- 9. Drop the column phone from customers.
  delete phone from cutomers;

-- 10. Truncate the orders table.
truncate table orders;

-- 11. Drop the table orders.
drop table orders;

-- 12. Rename table customers to client_master.
alter table customers rename client_master;

-- 13. Create a table products with price having DEFAULT 0.
create table products (product_id int,pname varchar(20),price int default 0);

-- 14. Add CASCADE DELETE rule between orders and customers.
alter table orders add constraint order_cid foreign key(order_cid) references customers(customer_id) on delete cascade;

-- 15. Show all tables in sales_db.
show tables;

-- 16. Insert 5 records into customers.
insert into customers values (101,"xyz","xyz@gmail",9120029380,1),
  (102,"lmn","lmn@gmail",7642576806), (103,"pqr","pqr@gmail",9876543298,2), 
   (104,"hello","hello@gmail",0987654321),(105,"abc","abc@gmail",9876234521,4);
 
 -- 17. Insert multiple rows into products in one query.
 insert into products values (1,"phone",20000),
 (2,"mirror",3000), (3,"cup",500), (4,"bed",29000), (5,"bulb",2000);
 
 -- 18. Update salary of employee id = 101.
 update employees set salary=27000 where emp_id=101;

-- 19. Increase product price by 10%.
update products set price = price+((price*10)/100);

-- 20. Delete a customer whose id = 10.
delete from customers where customer_id=101;

-- 21. Delete all inactive customers.
 -- alter table customers add column total_orders int ;
 delete from customers where total_orders is null;

-- 22. Insert a new order for customer id = 3.
insert into orders values(6,23000,'2026-12-01 09:06:59',103);

-- 23. Update order status to SHIPPED.
-- alter table orders add column status varchar(10);
update orders set status="SHIPPED";

-- 24. Delete orders older than 2022.
delete from orders where  YEAR(ordertime) < 2022;

-- 25. Copy data from customers to backup_customers.
create table backup_customers as select * from customers;

-- 26. Display all customers.
select * from customers;

-- 27. Display only name and email of customers.
select name,email from customers;

-- 28. Find all employees with salary > 50000.
select * from employees where salary > 50000;

-- 29. List products ordered by price DESC.
select * from products order by price DESC;


-- 30. Show distinct cities from customers.
 -- alter table customers add column city varchar(10); 
 -- update customers set city="satara" where customer_id in (101,103);
select distinct(city) from customers;

-- 31. Count total number of customers.
 select count(*) from customers;

-- 32. Find average salary of employees.
 select avg(salary) from employees;

-- 33. Find maximum product price.
 select max(price) from products;

-- 34. Find minimum order amount.
 select min(amount) from orders;

-- 35. Calculate total sales amount.
 select distinct sum(sales * quantity) as total_sales_amount from orders_practice group by product_id;

-- 36. Show department-wise employee count.
 select count(emp_id) from employees group by emp_department;

-- 37. Show department-wise average salary.
 select avg(salary) from employees group by emp_department;

-- 38. Display only departments having more than 5 employees.
select count(*),emp_department from employees group by emp_department having count(*)>5;

-- 39. Find total sales per customer.
select sum(sales) from orders_practice group by customer_id;

-- 40. Find total revenue per month 
select YEAR(order_Date) as orderyear , MONTH(order_Date) as ordermonth, sum(sales* quantity) as revenue from 
orders_practice group by orderyear,ordermonth order by orderyear,ordermonth;
--  select * from orders_practice;
 
-- 41. List employees who joined after 2021.
select emp_id from employees where date_joined > "2021-12-31";

-- 42. Show orders between two dates.
select order_id from orders where ordertime between ("11:02:45") AND ("20:19:07");

-- 43. Find customers from Delhi.
select customer_id from orders_practice where city="Delhi"; 

-- 44. Sort employees by salary ASC
select * from employees order by salary ASC;

-- 45. Display top 5 highest paid employees.
select emp_id from employees order by salary DESC limit 5;

-- 46. Show products priced between 1000 and 5000.
select pname from products where price between 1000 and 5000;

-- 47. Display customers whose name starts with A.
select * from customers where name like "A%";

-- 48. Find employees whose email contains hr.
select email from customers where email like "%hr%";

-- 49. List orders where status is NOT CANCELLED.
select * from orders where status != "Not Cancelled";

-- 50. Show customers with NULL phone numbers.
select * from customers where phone is null;

-- 51. Display customer names with their orders (INNER JOIN).
select c1.name,c1.customer_id,o1.order_id,o1.amount from customers c1 inner join orders o1 on c1.customer_id=o1.order_cid;

-- 52. Show all customers even if they have no orders (LEFT JOIN).
select * from customers c1 left join orders o1 on c1.customer_id=o1.order_cid;

-- 53. Show all orders even if customer is missing (RIGHT JOIN).
select * from orders o1 right  join customers c1 on o1.order_cid = c1.customer_id;

-- 54. Show employee names with department names.
select emp_id,emp_department from employees;

-- 55. List products with their category names.
select prodcut_name,category from orders_practice;

-- 56. Display orders with customer and product details.
select o1.order_id,o1.amount,c1.customer_id,c1.name,p1.pname from orders o1 inner join customers c1 on o1.order_cid=c1.customer_id inner join products p1 on p1.product_id=o1.order_id;

-- 57. Find employees working in IT department.
select emp_id from employees where emp_department="IT";

-- 58. Show customers who never placed an order.
select customer_id  from customers where customer_id NOT IN ( select order_cid from orders);

-- 59. Display departments with no employees.
select emp_department from employees where emp_id is null;

-- 60. List all possible combinations of customers and products (CROSS JOIN).
select * from customers cross join products;

-- 61. Find employees earning more than average salary.
select * from employees;
select emp_id from employees where salary > (select avg(salary) from employees);

-- 62. Find customers who placed the highest order.
select max(o1.amount),o1.order_cid,c1.name from orders o1 inner join customers c1 on o1.order_cid = c1.customer_id group by o1.order_cid limit 1;

-- 63. Find products costing more than average product price.
select pname from products where price > (select avg(price) from products);

-- 64. Find department with maximum employees.
select max(count(*)) from employees group by emp_department;

-- 65. Find employees who earn more than their manager.
select e1.emp_id from employees e1 join employees e2 on e1.emp_id = e2.manager_id having salary> e1.salary;

-- 66. Find customers who never placed any order.
select * from customers where total_orders is null;

-- 67. Find orders greater than average order value.
select * from orders where amount > (select avg(amount) from orders);

-- 68. Find the second highest salary using subquery.
select salary from employees where salary < (select max(salary) from employees) limit 1;

-- 69. Find products that were never sold.
select pname from products inner join orders on products.product_id = orders.order_id where order_id  is null;

-- 70. Find employees working in the same department as John.
select * from employees where emp_department = (select emp_department from employees where name="John");

-- 71. Create a view vw_active_customers.
create view vw_active_customers as select emp_id,emp_department,salary from employees;

-- 72. Create a view showing employee name, department, salary.
update vw_active_customers set emp_id =110 where salary = 10000;

-- 73. Update data using a view.
drop view vw_active_customers;

-- 74. Drop a view.
select * from orders_practice;

-- 75. Create a view for monthly sales summary.
create view montly_sales as select sum(sales*quantity) from orders_practice where order_date between "01-08-2020" and "01-09-2020";

-- 76. Create an index on email in customers table.
create index emailindex on customers(email);

-- 77. Create a composite index on orders(order_date, customer_id).
create index composite on orders(ordertime,order_cid);

-- 78. Drop an index.
alter table orders drop index composite;

-- 79. Show all indexes on employees.
select * from customers;

-- 81. Create a procedure to insert a new customer.
DELIMITER $$
USE `sales_db`$$
CREATE PROCEDURE `procedure1` () 
BEGIN 
    INSERT INTO customers VALUES (110, "spd", "spd@gmail", 3, "Mumbai"); 
END$$

DELIMITER ;

-- 82. Create a procedure to get employee details by id.
DELIMITER $$
USE `sales_db`$$
CREATE PROCEDURE `procedure22` () 
BEGIN 
    SELECT * FROM EMPLOYEES WHERE emp_id=101; 
END$$

DELIMITER ;


-- 83. Create a procedure to calculate total sales of a customer.
DELIMITER $$
USE `orders`$$
CREATE PROCEDURE `procedure33` ()
BEGIN 
 SELECT SUM(sales) FROM orders_practice GROUP BY customer_id;
 END$$
DELIMITER ;

-- 84. Create a procedure to update employee salary.
DELIMITER $$
USE `sales_db`$$
CREATE PROCEDURE `procedure4` ()
BEGIN 
UPDATE employees SET salary = 34000 WHERE emp_id=102;
 END$$
DELIMITER ;

-- 85. Call a stored procedure.
call procedure22;
call procedure33;


-- 86. Create a function to calculate GST for a given amount.
DELIMITER $$
USE `orders`$$
CREATE FUNCTION GST_CALCULATION(amount INTEGER)
RETURNS INTEGER
DETERMINISTIC
BEGIN
 RETURN (15*amount)/100;
END$$
DELIMITER ;
select GST_CALCULATION(20000);


-- quetion 87 not solvedDELIMITER $$
DELIMITER $$

CREATE FUNCTION get_employee_grade(salary DECIMAL(10,2))
RETURNS VARCHAR(2)
DETERMINISTIC
BEGIN
    DECLARE grade VARCHAR(2);
    IF salary >= 20000 THEN
        SET grade = 'A';
    ELSEIF salary >= 15000 THEN
        SET grade = 'B';
    ELSEIF salary >= 10000 THEN
        SET grade = 'C';
    ELSE
        SET grade = 'D';
    END IF;

    RETURN grade;
END $$
DELIMITER ;


-- 88. Use a function in a SELECT query.
DELIMITER $$
USE `orders`$$
CREATE FUNCTION emp_salaryy()
RETURNS INTEGER
DETERMINISTIC
BEGIN
DECLARE maxsalary BIGINT;
SELECT max(salary) INTO maxsalary from employees;
 RETURN maxsalary ;
END$$
DELIMITER ;
select emp_salaryy();

-- 89. Drop a function.
drop function emp_salary;

-- quetions 90 theory

-- 91. Create a trigger to log every insert on orders.
DELIMITER $$
USE `orders`$$
CREATE TRIGGER trigger3 
AFTER INSERT ON orders
FOR EACH ROW 
BEGIN
insert into orders values(NEW.order_id,NEW.amount,NEW.order_cid);
END;
DELIMITER;

-- 92. Create a trigger to prevent salary update below 30000.
DELIMITER $$
USE `orders`$$
CREATE TRIGGER trigger1 
BEFORE INSERT ON orders
FOR EACH ROW 
BEGIN
IF NEW.salary <30000 THEN
SET NEW.salary = NULL;
END IF;
END;
DELIMITER ;

-- 93. Create a trigger to auto-update stock after sale.
DELIMITER $$
USE `orders` $$
CREATE TRIGGER autoupdate 
AFTER UPDATE ON orders_practice
FOR EACH ROW 
BEGIN 
UPDATE orders_practice SET 
stock = orders_practice.stock - NEW.sale;
END ;
DELIMITER ;

-- 94. Create a BEFORE DELETE trigger on customers.
DELIMITER $$
CREATE TRIGGER beforedeleted 
BEFORE DELETE ON customers 
FOR EACH ROW 
BEGIN
SIGNAL SQLSTATE '01000' SET message_text="Warning Ypur are deleting data";
END$$
DELIMITER ;

-- 95. Drop a trigger.
 show triggers;
 use orders;
 DROP TRIGGER beforedelete;

start transaction;

insert into orders values(10,34000,'2026-01-13 12:20:40',103,"DISPATCHED");

commit;

-- 96. Insert records using START TRANSACTION.
start transaction;
insert into orders values(12,4000,'2026-01-13 01:27:41',105,"Cancelled");
-- 97. Use COMMIT after successful order insertion.
commit;


-- 99. Demonstrate SAVEPOINT usage.
savepoint reverseQuery;
insert into orders values(12,4000,'2026-01-13 01:27:41',105,"Cancelled");

-- 98. Use ROLLBACK when error occurs.
rollback to reverseQuery;
select * from orders;
commit;

-- 100.Simulate money transfer between two accounts using transaction control.
use orders;
create table account (id int, bank_number int, amount bigint);
ALTER TABLE account Modify column bank_number  varchar(20);
insert into account values (111, 'MB101',10000),(222,'BK201',23000);

start transaction;

update account set  amount = amount - 10000 where id = 1;
rollback;

update account set amount = amount + 10000 where id = 2;

commit;