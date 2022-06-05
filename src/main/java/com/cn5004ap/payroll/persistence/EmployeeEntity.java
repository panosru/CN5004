package com.cn5004ap.payroll.persistence;

import com.cn5004ap.payroll.common.Utils;
import com.cn5004ap.payroll.controller.SettingsController;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.Period;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
public class EmployeeEntity
    extends BaseEntity
{
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "iban", unique = true, nullable = false)
    private String iban;

    @Column(name = "ssn", unique = true, nullable = false)
    private String ssn;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "salary", nullable = false)
    private double grossSalary;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "employment_date", nullable = false)
    private Date employmentDate;

    @Column(name = "termination_date")
    private Date terminationDate;

    @Column(name = "last_updated")
    private Date lastUpdated;

    @OneToMany(mappedBy = "employee", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Cascade(org.hibernate.annotations.CascadeType.REPLICATE)
    private final Set<TransactionEntity> transactions = new HashSet<>();

    public EmployeeEntity()
    {}

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
        setGrossSalary(salary);
    }

    public EmployeeEntity(
        String firstName,
        String lastName,
        String email,
        Date birthDate,
        String gender,
        String department,
        String title,
        double salary)
    {
        this(firstName, lastName, email, department, title, salary);
        setBirthDate(birthDate);
        setGender(gender);
    }

    public EmployeeEntity(
        String firstName,
        String lastName,
        String email,
        String address,
        String phone,
        Date birthDate,
        String gender,
        String iban,
        String ssn,
        String department,
        String title,
        double salary,
        boolean active,
        Date employmentDate,
        Date terminationDate)
    {
        this(firstName, lastName, email, birthDate, gender, department, title, salary);
        setIban(iban);
        setSsn(ssn);
        setAddress(address);
        setPhone(phone);
        setActive(active);
        setEmploymentDate(employmentDate);
        setTerminationDate(terminationDate);
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

    public String getFullName(boolean reverse)
    {
        String[] fullName = {getFirstName(), getLastName()};

        if (reverse)
            Collections.reverse(Arrays.asList(fullName));

        return String.join(" ", fullName);
    }

    public String getFullName()
    {
        return getFullName(false);
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public Gender getGenderEnum()
    {
        return getGender().equals("F") ? Gender.FEMALE : Gender.MALE;
    }

    public String getIban()
    {
        return iban;
    }

    public void setIban(String iban)
    {
        this.iban = iban;
    }

    public String getSsn()
    {
        return ssn;
    }

    public void setSsn(String ssn)
    {
        this.ssn = ssn;
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

    public double getGrossSalary()
    {
        return grossSalary;
    }

    public void setGrossSalary(double salary)
    {
        this.grossSalary = salary;
    }

    public double getInsuranceExpense()
    {
        return getInsuranceExpense(getGrossSalary());
    }

    public static double getInsuranceExpense(double grossSalary)
    {
        return grossSalary * (SettingsController.getInsurancePercent() / 100);
    }

    public double getTaxExpense()
    {
        return getTaxExpense(getGrossSalary());
    }

    public static double getTaxExpense(double grossSalary)
    {
        return grossSalary * (SettingsController.getTaxPercent() / 100);
    }

    public double getNetSalary()
    {
        return getNetSalary(getGrossSalary(), getInsuranceExpense(), getTaxExpense());
    }

    public static double getNetSalary(double grossSalary, double insurance, double tax)
    {
        return grossSalary - insurance - tax;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public Status getStatus()
    {
        return isActive() ? Status.ACTIVE : Status.INACTIVE;
    }

    public String getStatusUnicode()
    {
        return isActive() ? "\u2713" : "\u2718";
    }

    public Date getEmploymentDate()
    {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate)
    {
        this.employmentDate = employmentDate;
    }

    public Period getEmploymentPeriod()
    {
        return Period.between(
            Utils.convertDateToLocal(getEmploymentDate()),
            Utils.convertDateToLocal(isActive() ? new Date() : getTerminationDate())
        );
    }

    public Date getTerminationDate()
    {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate)
    {
        this.terminationDate = terminationDate;
    }

    public Period getTerminationPeriod()
    {
        return Period.between(
            Utils.convertDateToLocal(getTerminationDate()),
            Utils.convertDateToLocal(new Date())
        );
    }

    public void activate()
    {
        setActive(true);
        setTerminationDate(null);
        setEmploymentDate(new Date());
    }

    public void terminate()
    {
        setActive(false);
        setTerminationDate(new Date());
    }

    public Set<TransactionEntity> getTransactions()
    {
        return transactions;
    }

    public void addTransaction(TransactionEntity transaction)
    {
        transactions.add(transaction);
    }

    public Date getLastUpdated()
    {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString()
    {
        return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ","
            + " department=" + department + ", title=" + title + ", salary=" + grossSalary + "]";
    }

    public enum Status
    {
        ACTIVE,
        INACTIVE
    }

    public enum Gender
    {
        MALE,
        FEMALE
    }
}
