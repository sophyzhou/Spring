#Data Access with JDBC
Spring provides a simplification in handling database access with the Spring JDBC. The Spring JDBC template has two advantages compared with standard JDBC:

 - The Spring JDBC template allows to clean-up the resources automatically.
 - The Spring JDBC template converts the standard JDBC SQLExceptions into RuntimeExceptions.  

 The Spring JDBC template is as following:
 >java.lang.Object
org.springframework.jdbc.support.JdbcAccessor
org.springframework.jdbc.core.JdbcTemplate

>public class JdbcTemplate
		extends JdbcAccessor
		implements JdbcOperations

The following example will demonstrate the usage of JDBC template.
We are assuming we have create a table inside the company database.
>create table employee(  
id number(10),  
name varchar2(100),  
salary number(10)  
);

  This class contains 3 properties with constructors and setter and getters.
```       
//Employee.java
package SpringJDBC;  
public class Employee {  
private int id;  
private String name;  
private float salary;  
//no-arg and parameterized constructors  
//getters and setters  
}
```     
Now let us create Dao(data access object)file. It contains one property jdbcTemplate and three methods saveEmployee(), updateEmployee and deleteEmployee().
```
package SpringJDBC;  
import org.springframework.jdbc.core.JdbcTemplate;  

public class EmployeeDao {  
private JdbcTemplate jdbcTemplate;  

public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
    this.jdbcTemplate = jdbcTemplate;  
}  

public int saveEmployee(Employee e){  
    String query="insert into employee values(  
    '"+e.getId()+"','"+e.getName()+"','"+e.getSalary()+"')";  
    return jdbcTemplate.update(query);  
}  
public int updateEmployee(Employee e){  
    String query="update employee set   
    name='"+e.getName()+"',salary='"+e.getSalary()+"' where id='"+e.getId()+"' ";  
    return jdbcTemplate.update(query);  
}  
public int deleteEmployee(Employee e){  
    String query="delete from employee where id='"+e.getId()+"' ";  
    return jdbcTemplate.update(query);  
}  

}  
```
Bean.xml

The DriverManagerDataSource is used to contain the information about the database such as driver class name, connnection URL, username and password.

There are a property named datasource in the JdbcTemplate class of DriverManagerDataSource type. So, we need to provide the reference of DriverManagerDataSource object in the JdbcTemplate class for the datasource property.

Here, we are using the JdbcTemplate object in the EmployeeDao class, so we are passing it by the setter method but you can use constructor also.
```
<?xml version="1.0" encoding="UTF-8"?>  
<beans  
    xmlns="http://www.springframework.org/schema/beans"  
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  
    xmlns:p="http://www.springframework.org/schema/p"  
    xsi:schemaLocation="http://www.springframework.org/schema/beans   
http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">  

<bean id="ds" class="org.springframework.jdbc.datasource.DriverManagerDataSource">  
<property name="driverClassName" value="oracle.jdbc.driver.OracleDriver" />  
<property name="url" value="jdbc:oracle:thin:@localhost:1521:xe" />  
<property name="username" value="system" />  
<property name="password" value="oracle" />  
</bean>  

<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">  
<property name="dataSource" ref="ds"></property>  
</bean>  

<bean id="edao" class="Spring.EmployeeDao">  
<property name="jdbcTemplate" ref="jdbcTemplate"></property>  
</bean>  

</beans>
```
main.java
This class gets the bean from the Bean.xml file and calls the saveEmployee() method. We can also call updateEmployee() and deleteEmployee() method by uncommenting the code as well.
```
package SpringJDBC;  

import org.springframework.context.ApplicationContext;  
import org.springframework.context.support.ClassPathXmlApplicationContext;  
public class Test {  

public static void main(String[] args) {  
    ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");  

    EmployeeDao dao=(EmployeeDao)ctx.getBean("edao");  
    int status=dao.saveEmployee(new Employee(007,"Elsa",105000));  
    System.out.println(status);       
}  

}  
```
With this simple example, Spring JDBC template class  can executes SQL queries, update statements and stored procedure calls. Instance of the JDBC template class are threadsafe once configured. So we can configure a single instance of a JdbcTemplate and then safely inject this shared reference into multiple DAOs.

[HOME](README.md)
[NEXT](Spring_mvc.md)
