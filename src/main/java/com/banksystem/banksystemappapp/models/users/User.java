package com.banksystem.banksystemappapp.models.users;

import com.banksystem.banksystemappapp.models.bank.Bank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@DynamicUpdate
@Table(name = "User")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String password;

    private String name;

    @Column(unique = true)
    private String userName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    public User() {
    }


    public User(String name, String userName, String password) {
        setName(name);
        setUserName(userName);
        setPassword(password);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && name.equals(user.name) && userName.equals(user.userName) && password.equals(user.password) && bank.equals(user.bank) && roles.equals(user.roles);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", bank=" + bank +
                ", roles=" + roles +
                '}';
    }
}
