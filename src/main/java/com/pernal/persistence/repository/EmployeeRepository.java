package com.pernal.persistence.repository;

import com.pernal.model.EmployeeSearchReq;
import com.pernal.persistence.connection.HibernateUtil;
import com.pernal.persistence.entity.EmployeeEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class EmployeeRepository implements IEmployeeRepository {

    private Logger logger = LoggerFactory.getLogger(EmployeeRepository.class);

    @Override
    public int deleteById(Long id) throws HibernateException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            logger.info("Delete entity started...");

            transaction = session.beginTransaction();
            Optional<EmployeeEntity> employeeEntityOpt = findById(id);
            employeeEntityOpt.ifPresent(session::delete);
            transaction.commit();

            logger.info("Delete entity finished");

            return 1;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new HibernateException("Error while deleting entity with id: " + id);
        }
    }

    @Override
    public <S extends EmployeeEntity> S save(S s) throws HibernateException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            logger.info("Save entity started...");

            transaction = session.beginTransaction();
            session.save(s);
            transaction.commit();

            logger.info("Save entity finished");

            return s;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new HibernateException("Error while saving entity");
        }
    }

    @Override
    public Optional<EmployeeEntity> findById(Long id) throws HibernateException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            logger.info("Get entity started...");

            transaction = session.beginTransaction();
            EmployeeEntity employeeEntity = session.get(EmployeeEntity.class, id.intValue());
            transaction.commit();

            logger.info("Get entity finished");

            return Optional.ofNullable(employeeEntity);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new HibernateException("Error while selecting entity with id: " + id);
        }
    }

    @Override
    public EmployeeEntity update(EmployeeEntity employeeEntity) throws HibernateException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            logger.info("Update entity started...");

            transaction = session.beginTransaction();
            Optional<EmployeeEntity> employeeEntityOpt = findById(employeeEntity.getId().longValue());
            if (employeeEntityOpt.isPresent()) {
                session.update(employeeEntity);
            }
            transaction.commit();

            logger.info("Update entity finished");

            return employeeEntity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new HibernateException("Error while updating entity with id: " + employeeEntity.getId());
        }
    }

    @Override
    public List<EmployeeEntity> search(Map<String, Object> parametersMap) throws HibernateException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            logger.info("Searching entity started...");

            Query<EmployeeEntity> searchQuery = session.createQuery(createSearchQuery(parametersMap), EmployeeEntity.class);
            parametersMap.forEach(searchQuery::setParameter);

            logger.info("Searching entity finished");

            return searchQuery.getResultList();
        } catch (Exception e) {
            throw new HibernateException("Error while searching entity");
        }
    }

    private String createSearchQuery(Map<String, Object> parametersMap) {
        StringBuilder stringBuilder = new StringBuilder("from EmployeeEntity E where 1=1");

        parametersMap.keySet().forEach(field -> stringBuilder.append(" and " + field + " = :" + field));

        return stringBuilder.toString();
    }
}
