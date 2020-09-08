package com.pernal.mapper;

import com.pernal.model.Employee;
import com.pernal.model.EmployeeReq;
import com.pernal.persistence.entity.EmployeeEntity;

public class EmployeeMapper {

    public static Employee mapToEmployee(EmployeeEntity employeeEntity) {
        if (employeeEntity != null) {
            Employee employee = new Employee();

            employee.setGrade(employeeEntity.getGrade());
            employee.setName(employeeEntity.getName());
            employee.setSalary(employeeEntity.getSalary());
            employee.setSurname(employeeEntity.getSurname());
            employee.setId(employeeEntity.getId());

            return employee;
        }

        return null;
    }

    public static EmployeeEntity mapToEntity(EmployeeReq employeeReq) {
        if (employeeReq != null) {
            EmployeeEntity employeeEntity = new EmployeeEntity();

            employeeEntity.setGrade(employeeReq.getGrade());
            employeeEntity.setName(employeeReq.getName());
            employeeEntity.setSalary(employeeReq.getSalary());
            employeeEntity.setSurname(employeeReq.getSurname());

            return employeeEntity;
        }

        return null;
    }
}
