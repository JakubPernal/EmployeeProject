package com.pernal.service;

import com.pernal.model.Employee;
import com.pernal.model.EmployeeReq;
import com.pernal.model.EmployeeSearchReq;
import com.pernal.model.EmployeeServiceResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {

    ResponseEntity<EmployeeServiceResponse<Void>> insert(EmployeeReq employeeReq);

    ResponseEntity<EmployeeServiceResponse<Employee>> get(Long id);

    ResponseEntity<EmployeeServiceResponse<Void>> delete(Long id);

    ResponseEntity<EmployeeServiceResponse<Void>> update(Long id, EmployeeReq employeeReq);

    ResponseEntity<EmployeeServiceResponse<List<Employee>>> search(EmployeeSearchReq employeeSearchReq);
}
