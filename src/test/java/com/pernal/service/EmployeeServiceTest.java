package com.pernal.service;

import com.pernal.model.Employee;
import com.pernal.model.EmployeeReq;
import com.pernal.model.EmployeeSearchReq;
import com.pernal.model.EmployeeServiceResponse;
import com.pernal.persistence.entity.EmployeeEntity;
import com.pernal.persistence.repository.EmployeeRepository;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldInsert() {
        ResponseEntity<EmployeeServiceResponse<Void>> responseResponseEntity = employeeService.insert(new EmployeeReq());

        assertEquals(200, responseResponseEntity.getBody().getStatus().value());
        assertEquals("Employee has been added", responseResponseEntity.getBody().getMessage());
    }

    @Test
    public void shouldThrowWhileInsert() {
        given(employeeRepository.save(any())).willThrow(new HibernateException("msg"));

        ResponseEntity<EmployeeServiceResponse<Void>> responseResponseEntity = employeeService.insert(new EmployeeReq());

        assertEquals(500, responseResponseEntity.getBody().getStatus().value());
        assertEquals("msg", responseResponseEntity.getBody().getMessage());
    }

    @Test
    public void shouldGet() {
        given(employeeRepository.findById(1L)).willReturn(Optional.of(new EmployeeEntity()));
        ResponseEntity<EmployeeServiceResponse<Employee>> responseResponseEntity = employeeService.get(1L);

        assertEquals(200, responseResponseEntity.getBody().getStatus().value());
        assertEquals("OK", responseResponseEntity.getBody().getMessage());
    }

    @Test
    public void shouldNotGet() {
        given(employeeRepository.findById(1L)).willReturn(Optional.empty());
        ResponseEntity<EmployeeServiceResponse<Employee>> responseResponseEntity = employeeService.get(1L);

        assertEquals(404, responseResponseEntity.getBody().getStatus().value());
        assertEquals("Entity with given id not found in database", responseResponseEntity.getBody().getMessage());
    }

    @Test
    public void shouldThrowWhileGet() {
        given(employeeRepository.findById(1L)).willThrow(new HibernateException("msg"));
        ResponseEntity<EmployeeServiceResponse<Employee>> responseResponseEntity = employeeService.get(1L);

        assertEquals(500, responseResponseEntity.getBody().getStatus().value());
        assertEquals("msg", responseResponseEntity.getBody().getMessage());
    }

    @Test
    public void shouldDelete() {
        ResponseEntity<EmployeeServiceResponse<Void>> responseResponseEntity = employeeService.delete(1L);

        assertEquals(200, responseResponseEntity.getBody().getStatus().value());
        assertEquals("OK", responseResponseEntity.getBody().getMessage());
    }

    @Test
    public void shouldThrowWhileDelete() {
        given(employeeRepository.deleteById(1L)).willThrow(new HibernateException("msg"));
        ResponseEntity<EmployeeServiceResponse<Void>> responseResponseEntity = employeeService.delete(1L);

        assertEquals(500, responseResponseEntity.getBody().getStatus().value());
        assertEquals("msg", responseResponseEntity.getBody().getMessage());
    }

    @Test
    public void shouldUpdate() {
        ResponseEntity<EmployeeServiceResponse<Void>> responseResponseEntity = employeeService.update(1L, new EmployeeReq());

        assertEquals(200, responseResponseEntity.getBody().getStatus().value());
        assertEquals("OK", responseResponseEntity.getBody().getMessage());
    }

    @Test
    public void shouldThrowWhenUpdate() {
        given(employeeRepository.update(any())).willThrow(new HibernateException("msg"));

        ResponseEntity<EmployeeServiceResponse<Void>> responseResponseEntity = employeeService.update(1L, new EmployeeReq());

        assertEquals(500, responseResponseEntity.getBody().getStatus().value());
        assertEquals("msg", responseResponseEntity.getBody().getMessage());
    }

    @Test
    public void shouldSearchForEmptyFields() {
        given(employeeRepository.search(any())).willReturn(new ArrayList<>());

        ResponseEntity<EmployeeServiceResponse<List<Employee>>> responseResponseEntity = employeeService.search(new EmployeeSearchReq());

        assertNotNull(responseResponseEntity.getBody().getBody());
        assertEquals(200, responseResponseEntity.getBody().getStatus().value());
        assertEquals("OK", responseResponseEntity.getBody().getMessage());
    }

    @Test
    public void shouldSearchForFilledFields() {
        EmployeeSearchReq employeeSearchReq = new EmployeeSearchReq();
        employeeSearchReq.setGrade(10);
        employeeSearchReq.setName("name");
        employeeSearchReq.setSurname("surname");
        employeeSearchReq.setSalary(100);

        given(employeeRepository.search(any())).willReturn(new ArrayList<>());

        ResponseEntity<EmployeeServiceResponse<List<Employee>>> responseResponseEntity = employeeService.search(employeeSearchReq);

        assertNotNull(responseResponseEntity.getBody().getBody());
        assertEquals(200, responseResponseEntity.getBody().getStatus().value());
        assertEquals("OK", responseResponseEntity.getBody().getMessage());
    }

    @Test
    public void shouldThrowWhileSearch() {
        given(employeeRepository.search(any())).willThrow(new HibernateException("msg"));

        ResponseEntity<EmployeeServiceResponse<List<Employee>>> responseResponseEntity = employeeService.search(new EmployeeSearchReq());

        assertEquals(500, responseResponseEntity.getBody().getStatus().value());
        assertEquals("msg", responseResponseEntity.getBody().getMessage());
    }

}
