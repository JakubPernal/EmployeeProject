package com.pernal.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class EmployeeReq implements Serializable {

    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private Integer grade;
    @NotNull
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
