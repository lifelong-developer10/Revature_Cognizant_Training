
-- practise questions for joins
-- quetion 1:
select employees.employee_id,departments.department_id, locations.city from employees left join departments on employees.department_id = departments.department_id inner join locations on departments.location_id= locations.location_id;

-- question 2:
select * from employees inner join departments on employees.department_id= departments.department_id where departments.department_name is null;
 
-- question3:
select * from job_history;
select employees.employee_id,employees.first_name  from employees left join job_history on  employees.employee_id=job_history.employee_id  where job_history.employee_id is null;

-- question 4:
select e.employee_id,e.first_name,count(j.job_id) from employees e left join job_history j
on e.employee_id = j.employee_id group by e.employee_id
UNION 
select e.employee_id,e.first_name,count(j.job_id) from employees e right join job_history  j
on e.employee_id = j.employee_id  group by e.employee_id ;

-- quetion 5:
select  employee_id,
salary  from employees e1 where salary >=
(select avg(salary) from employees e2 where e1.employee_id = e2.employee_id group by department_id);

-- question 6 :
select max(employees.salary), departments.department_id,departments.department_name from employees inner join departments on employees.department_id = departments.department_id group by department_id;

-- quetion 8:
select * from employees;
select e1.employee_id as manager_id,e2.employee_id as employee_id,e1.salary as manager_salary from employees e1 join employees e2 on e1.employee_id=e2.manager_id where e1.salary < e2.salary;

-- quetion 9:departments
select * from employees;

select department_id,sum(salary) from employees  group by department_id having sum(salary) > 300000;


-- quetion 10:
select * from employees;
select count(job_history.job_id),employees.employee_id,employees.first_name from job_history inner join employees on employees.employee_id = job_history.employee_id group by employees.employee_id;

-- question  11 :
select * from departments;
select * from job_history;
 select department_id,department_name from departments where department_id NOT IN(select departments.department_id from departments inner join job_history on departments.department_id = job_history.department_id); 

-- quetion 12:
select * from job_history;
select * from departments;
select manager_id,count(employee_id) from employees group by manager_id having count(employee_id) > 5;

-- question 13:
select * from employees;
select employees.employee_id,employees.department_id,employees.job_id  from employees where job_id not in (select job_id from job_history); 

-- question 14:
select * from locations;
select * from departments;
select * from employees;
select * from regions;
select * from countries;

 select e1.employee_id,d1.department_id,c1.country_name from employees e1
 join departments d1 on e1.department_id = d1.department_id 
 join  locations l1 on d1.location_id = l1.location_id
 join countries c1 on l1.country_id = c1.country_id  group by 
 e1.employee_id, c1.region_id
 having count(distinct d1.department_id)=
 (select count(distinct d2.department_id) from departments d2 
 join locations l2  on d2.location_id = l2.location_id
 join countries c2 on l2.country_id = c2.country_id
 where c2.region_id = c1.region_id);

-- quetion 15:
select departments.department_id from departments inner join
 employees on departments.department_id = employees.department_id 
 inner join jobs on employees.job_id = jobs.job_id  where  employees.salary > jobs.min_salary
 group by departments.department_id ; 
 
