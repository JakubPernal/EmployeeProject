package com.pernal.persistence.repository;

import com.pernal.model.EmployeeSearchReq;
import com.pernal.persistence.entity.EmployeeEntity;
import org.hibernate.HibernateException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IEmployeeRepository {
    int deleteById(Long id) throws HibernateException;

    <S extends EmployeeEntity> S save(S s) throws HibernateException;

    Optional<EmployeeEntity> findById(Long id) throws HibernateException;

    EmployeeEntity update(EmployeeEntity employeeEntity) throws HibernateException;

    List<EmployeeEntity> search(Map<String, Object> parametersMap) throws HibernateException;
}
