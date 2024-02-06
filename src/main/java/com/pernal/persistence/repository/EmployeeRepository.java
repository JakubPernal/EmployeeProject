package com.pernal.persistence.repository;

import com.pernal.persistence.entity.EmployeeEntity;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class EmployeeRepository implements IEmployeeRepository {

    private Logger logger = LoggerFactory.getLogger(EmployeeRepository.class);

    private EntityManager entityManager;

    public EmployeeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public int deleteById(Long id) throws HibernateException {
        logger.info("Delete entity started...");

        Optional<EmployeeEntity> byId = findById(id);
        byId.ifPresent(entity -> entityManager.remove(entity));

        logger.info("Delete entity finished");

        return 1;
    }

    @Override
    public <S extends EmployeeEntity> S save(S s) throws HibernateException {
            logger.info("Save entity started...");

            entityManager.persist(s);

            logger.info("Save entity finished");

            return s;
    }

    @Override
    public Optional<EmployeeEntity> findById(Long id) throws HibernateException {
            logger.info("Get entity started...");

            EmployeeEntity employeeEntity = entityManager.find(EmployeeEntity.class, id.intValue());

            logger.info("Get entity finished");

            return Optional.ofNullable(employeeEntity);
    }

    @Override
    public EmployeeEntity update(EmployeeEntity employeeEntity) throws HibernateException {
            logger.info("Update entity started...");

            Optional<EmployeeEntity> employeeEntityOpt = findById(employeeEntity.getId().longValue());
            if (employeeEntityOpt.isPresent()) {
                entityManager.merge(employeeEntity);
            }

            logger.info("Update entity finished");

            return employeeEntity;
    }

    @Override
    public List<EmployeeEntity> search(Map<String, Object> parametersMap) throws HibernateException {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            logger.info("Searching entity started...");
//
//            Query<EmployeeEntity> searchQuery = session.createQuery(createSearchQuery(parametersMap), EmployeeEntity.class);
//            parametersMap.forEach(searchQuery::setParameter);
//
//            logger.info("Searching entity finished");
//
//            return searchQuery.getResultList();
//        } catch (Exception e) {
//            throw new HibernateException("Error while searching entity");
//        }

        return Collections.emptyList();
    }

    private String createSearchQuery(Map<String, Object> parametersMap) {
        StringBuilder stringBuilder = new StringBuilder("from EmployeeEntity E where 1=1");

        parametersMap.keySet().forEach(field -> stringBuilder.append(" and " + field + " = :" + field));

        return stringBuilder.toString();
    }
}
