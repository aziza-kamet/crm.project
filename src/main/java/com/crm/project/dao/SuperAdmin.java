package com.crm.project.dao;

import javax.persistence.*;

/**
 * Created by aziza on 28.10.17.
 */

@Entity
@Table(name = "super_admins")
public class SuperAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String  password;

    @Column(name = "active", insertable = false, columnDefinition = "INT(1) DEFAULT '1'")
    private Integer active;

    public SuperAdmin() {
    }

    public SuperAdmin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}
