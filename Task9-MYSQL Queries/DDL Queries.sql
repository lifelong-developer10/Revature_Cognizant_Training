-- 2.1 Select the last name of all employees.
select last_name from employees;


-- 2.2 Select the last name of all employees, without duplicates.
select distinct last_name from employees;

-- 2.3 Select all the data of employees whose last name is "Smith".
select * from employees where last_name = "Smith";

-- 2.4 Select all the data of employees whose last name is "Smith" or "Doe".
select * from employees where last_name = "Smith" OR  last_name="Doe";

-- 2.5 Select all the data of employees that work in department 14.
select * from employees where department_id=14;

-- 2.6 Select all the data of employees that work in department 37 or department 77.
select * from employees where department_id= 37 OR  department_id=77;

-- 2.7 Select all the data of employees whose last name begins with an "S".
select * from employees where last_name like "S%";

-- 2.8 Select the sum of all the departments' budgets.
select department_id,SUM(salary) AS Budget from employees group by department_id;

-- 2.9 Select the number of employees in each department (you only need to show the department code and the number of employees).
select count(employee_id),department_id from employees group by department_id;

-- 2.10 Select all the data of employees, including each employee's department's data.
select * from employees inner join departments d1 on employees.department_id = d1.department_id;


-- 2.11 Select the name and last name of each employee, along with the name and budget of the employee's department.
SELECT emp.first_name, emp.last_name, d1.department_name,  SUM(emp.salary) OVER(PARTITION BY emp.department_id) AS Budget FROM employees emp
INNER JOIN departments d1 ON emp.department_id = d1.department_id;

-- 2.12 Select the name and last name of employees working for departments with a budget greater than $60,000.
select sum(salary) as budget from employees group by department_id having sum(salary)>60000 ; 


-- 2.13 Select the departments with a budget larger than the average budget of all the departments.
 select department_id,sum(salary) as budget from employees group by department_id  having avg(salary)<sum(salary); 


-- 2.14 Select the names of departments with more than two employees.
select e1.department_id,count(e1.employee_id),d1.department_name from employees e1 inner join departments d1 on e1.department_id = d1.department_id group by department_id having count(employee_id) > 2;

-- 2.15 Very Important - Select the name and last name of employees working for departments with second lowest budget.
select first_name,last_name from employees where department_id = (select department_id
 from employees group by department_id order by sum(salary)  DESC  limit 1  offset 1);

-- 2.16  Add a new department called "Quality Assurance", with a budget of $40,000 and departmental code 11. 
-- And Add an employee called "Mary Moore" in that department, with SSN 847-21-9811.
update employees set first_name="Mary", last_name = "Moore",salary=40000 where employee_id=208 ;
       update departments set department_id=11, department_name="Quality Assurance" where department_id=110;

-- 2.17 Reduce the budget of all departments by 10%.
select sum((salary*10)/100) from employees;

-- 2.18 Reassign all employees from the Research department (code 77) to the IT department (code 14).
 update employees set department_id=77 where department_id=14;

-- 2.19 Delete from the table all employees in the IT department (code 14).
delete employee_id from employees where department_id = 44;

-- 2.20 Delete from the table all employees who work in departments with a budget greater than or equal to $60,000.delete ANSWER:employee_id from employees where department_id in (select department_id from employees  group by department_id having sum(salary) >= 60000);
delete employee_id from employees where department_id  in (select department_id from employees group by department_id having sum(salary) >60000);

-- 2.21 Delete from the table all employees.
truncate table employees;