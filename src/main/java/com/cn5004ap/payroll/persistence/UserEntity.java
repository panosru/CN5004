package com.cn5004ap.payroll.persistence;

import com.cn5004ap.payroll.common.Utils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "users")
public class UserEntity
    extends BaseEntity
{
    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @Email
    @NotBlank
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank
    @Column(name = "password")
    private String password;

    @Column(name = "token", unique = true)
    private String token;

    public UserEntity()
    { }

    public UserEntity(String firstName, String lastName, String email)
    {
        this();
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }

    public UserEntity(String firstName, String lastName, String email, String username, String password)
    {
        this(firstName, lastName, email);
        setUsername(username);
        setPassword(password);
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

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = Utils.hashString(password);
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    @Override
    public String toString()
    {
        return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
    }
}
