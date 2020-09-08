package com.pernal.mapper;

import com.pernal.model.Employee;
import com.pernal.model.EmployeeReq;
import com.pernal.persistence.entity.EmployeeEntity;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EmployeeMapperTest {

    @Test
    public void shouldMap() {
        EmployeeReq employeeReq = createEmployee();

        EmployeeEntity employeeEntity = EmployeeMapper.mapToEntity(employeeReq);
        employeeEntity.setId(1);
        Employee remappedEmployee = EmployeeMapper.mapToEmployee(employeeEntity);

        assertEquals(employeeEntity.getId().intValue(), remappedEmployee.getId());
        assertEquals(employeeEntity.getName(), remappedEmployee.getName());
        assertEquals(employeeEntity.getSurname(), remappedEmployee.getSurname());
        assertEquals(employeeEntity.getSalary(), remappedEmployee.getSalary());
        assertEquals(employeeEntity.getGrade(), remappedEmployee.getGrade());
    }

    @Test
    public void shouldNotMap() {
        EmployeeEntity employeeEntity = EmployeeMapper.mapToEntity(null);
        Employee employee = EmployeeMapper.mapToEmployee(null);

        assertNull(employee);
        assertNull(employeeEntity);
    }

    private EmployeeReq createEmployee() {
        EmployeeReq employeeReq = new EmployeeReq();

        employeeReq.setName("name");
        employeeReq.setSurname("surname");
        employeeReq.setGrade(10);
        employeeReq.setSalary(100);

        return employeeReq;
    }

}
