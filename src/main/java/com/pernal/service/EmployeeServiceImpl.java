package com.pernal.service;

import com.pernal.mapper.EmployeeMapper;
import com.pernal.model.Employee;
import com.pernal.model.EmployeeReq;
import com.pernal.model.EmployeeSearchReq;
import com.pernal.model.EmployeeServiceResponse;
import com.pernal.persistence.entity.EmployeeEntity;
import com.pernal.persistence.repository.EmployeeRepository;
import com.pernal.persistence.repository.IEmployeeRepository;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private IEmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ResponseEntity<EmployeeServiceResponse<Void>> insert(EmployeeReq employeeReq) {
        try {
            EmployeeEntity employeeEntity = EmployeeMapper.mapToEntity(employeeReq);
            employeeRepository.save(employeeEntity);

            return ResponseEntity.ok(EmployeeServiceResponse.emptyBodyResponse(HttpStatus.OK, "Employee has been added"));
        } catch (HibernateException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(EmployeeServiceResponse.emptyBodyResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }

    }

    @Override
    public ResponseEntity<EmployeeServiceResponse<Employee>> get(Long id) {
        try {
            Optional<EmployeeEntity> employeeEntityOptional = employeeRepository.findById(id);

            if (employeeEntityOptional.isPresent()) {
                Employee employee = EmployeeMapper.mapToEmployee(employeeEntityOptional.get());
                return ResponseEntity.ok(EmployeeServiceResponse.createResponse(employee, HttpStatus.OK, "OK"));
            } else {
                logger.warn("Entity with given id not found in database");
                return ResponseEntity.ok(EmployeeServiceResponse.createResponse(null, HttpStatus.NOT_FOUND, "Entity with given id not found in database"));
            }
        } catch (HibernateException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(EmployeeServiceResponse.emptyBodyResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<EmployeeServiceResponse<Void>> delete(Long id) {
        try {
            employeeRepository.deleteById(id);

            return ResponseEntity.ok(EmployeeServiceResponse.emptyBodyResponse(HttpStatus.OK, "OK"));
        } catch (HibernateException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(EmployeeServiceResponse.emptyBodyResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<EmployeeServiceResponse<Void>> update(Long id, EmployeeReq employeeReq) {
        try {
            EmployeeEntity employeeEntity = EmployeeMapper.mapToEntity(employeeReq);
            employeeEntity.setId(id.intValue());
            employeeRepository.update(employeeEntity);

            return ResponseEntity.ok(EmployeeServiceResponse.emptyBodyResponse(HttpStatus.OK, "OK"));
        } catch (HibernateException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(EmployeeServiceResponse.emptyBodyResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<EmployeeServiceResponse<List<Employee>>> search(EmployeeSearchReq employeeSearchReq) {
        try {
            Map<String, Object> parametersMap = createParametersMap(employeeSearchReq);
            List<EmployeeEntity> employeeEntities = employeeRepository.search(parametersMap);
            List<Employee> employees = employeeEntities.stream().map(EmployeeMapper::mapToEmployee).collect(Collectors.toList());

            return ResponseEntity.ok(EmployeeServiceResponse.createResponse(employees, HttpStatus.OK, "OK"));
        } catch (HibernateException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(EmployeeServiceResponse.emptyBodyResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    private Map<String, Object> createParametersMap(EmployeeSearchReq employeeSearchReq) {
        Map<String, Object> parametersMap = new HashMap<>();

        if (employeeSearchReq.getName() != null) {
            parametersMap.put("name", employeeSearchReq.getName());
        }
        if (employeeSearchReq.getSurname() != null) {
            parametersMap.put("surname", employeeSearchReq.getSurname());
        }
        if (employeeSearchReq.getGrade() != null) {
            parametersMap.put("grade", employeeSearchReq.getGrade());
        }
        if (employeeSearchReq.getSalary() != null) {
            parametersMap.put("salary", employeeSearchReq.getSalary());
        }

        return parametersMap;
    }
}
