package com.cn5004ap.payroll.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class EmployeeEntity
    extends BaseEntity
{
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "department")
    private String department;

    @Column(name = "title")
    private String title;

    @Column(name = "salary")
    private double salary;

    public EmployeeEntity()
    { }

    public EmployeeEntity(String firstName, String lastName, String email)
    {
        this();
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }

    public EmployeeEntity(
        String firstName,
        String lastName,
        String email,
        String department,
        String title,
        double salary)
    {
        this(firstName, lastName, email);
        setDepartment(department);
        setTitle(title);
        setSalary(salary);
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public double getSalary()
    {
        return salary;
    }

    public void setSalary(double salary)
    {
        this.salary = salary;
    }

    @Override
    public String toString()
    {
        return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", department=" + department + ", title=" + title + ", salary=" + salary + "]";
    }
}
