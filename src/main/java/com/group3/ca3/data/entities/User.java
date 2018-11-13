package com.group3.ca3.data.entities;

import com.group3.ca3.logic.jwt.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;


@NamedQueries(
        {

                @NamedQuery(name = "User.findById",
                        query = "SELECT u FROM User u WHERE u.id = :id"),

                @NamedQuery(name = "User.findByEmail",
                        query = "SELECT u FROM User u WHERE u.email = :email")
        }
)


@Entity
@Table(name = "user_")
public class User
{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<File> files = new ArrayList<>();

    public User(String name, String passwordHash, String email)
    {
        this.name = name;
        this.passwordHash = passwordHash;
        this.email = email;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPasswordHash()
    {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash)
    {
        this.passwordHash = passwordHash;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Role getRole()
    {
        return this.role;
    }

    public User setRole(Role role)
    {
        this.role = role;
        return this;
    }

    public List<File> getFiles()
    {
        return this.files;
    }

    public User setFiles(List<File> files)
    {
        this.files = files;
        return this;
    }
}
