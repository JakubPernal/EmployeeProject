package com.pernal.model;

import java.io.Serializable;

/**
 * @author pla067jakpern, wrz 08, 2020 CRIF IT Solutions Poland
 **/
public class EmployeeSearchReq implements Serializable {
    private String name;
    private String surname;
    private Integer grade;
    private Integer salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}
