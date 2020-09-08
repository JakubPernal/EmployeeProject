package com.pernal.repository;

import com.pernal.configuration.EmployeeConfig;
import com.pernal.persistence.entity.EmployeeEntity;
import com.pernal.persistence.repository.IEmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @author pla067jakpern, wrz 07, 2020 CRIF IT Solutions Poland
 **/

@SpringBootTest
public class EmployeeRepositoryTest {

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Test
    @Transactional
    public void shouldInsertEmployee() {
        EmployeeEntity employeeEntity = createEmployee(null, "name", "surname", 100, 10);

        EmployeeEntity savedEmployeeEntity = assertDoesNotThrow(() -> employeeRepository.save(employeeEntity));

        assertEquals(employeeEntity.getName(), savedEmployeeEntity.getName());
        assertEquals(employeeEntity.getSurname(), savedEmployeeEntity.getSurname());
        assertEquals(employeeEntity.getSalary(), savedEmployeeEntity.getSalary());
        assertEquals(employeeEntity.getGrade(), savedEmployeeEntity.getGrade());
        assertNotNull(savedEmployeeEntity.getId());
    }

    @Test
    @Transactional
    public void shouldFindById() {
        Optional<EmployeeEntity> employeeEntityOptional = assertDoesNotThrow(() -> employeeRepository.findById(100L));

        assertTrue(employeeEntityOptional.isPresent());
        EmployeeEntity employeeEntity = employeeEntityOptional.get();

        assertEquals("Jakub", employeeEntity.getName());
        assertEquals("Pernal", employeeEntity.getSurname());
        assertEquals(10, employeeEntity.getGrade().intValue());
        assertEquals(1000, employeeEntity.getSalary().intValue());
        assertEquals(100, employeeEntity.getId().intValue());
    }

    @Test
    @Transactional
    public void shouldNotFindById() {
        Optional<EmployeeEntity> employeeEntityOptional = assertDoesNotThrow(() -> employeeRepository.findById(101L));

        assertFalse(employeeEntityOptional.isPresent());
    }

    @Test
    public void shouldDeleteById() {
        this.delete();
    }

    @Transactional
    public void delete() {
        assertDoesNotThrow(() -> employeeRepository.deleteById(100L));
        assertDoesNotThrow(() -> employeeRepository.deleteById(101L));
        Optional<EmployeeEntity> employeeEntityOptional = assertDoesNotThrow(() -> employeeRepository.findById(100L));

        assertFalse(employeeEntityOptional.isPresent());
    }

    @Test
    public void shouldUpdate() {
        this.update();
    }

    @Transactional
    public void update() {
        EmployeeEntity employeeEntity = createEmployee(null, "name", "surname", 100, 10);

        EmployeeEntity savedEmployeeEntity = assertDoesNotThrow(() -> employeeRepository.save(employeeEntity));
        savedEmployeeEntity.setSurname("Pernal");
        savedEmployeeEntity.setGrade(10);
        savedEmployeeEntity.setSalary(1000);
        savedEmployeeEntity.setName("Jakub");

        assertDoesNotThrow(() -> employeeRepository.update(savedEmployeeEntity));
    }

    @Test
    @Transactional
    public void shouldSearchAll() {
        List<EmployeeEntity> employeeEntities = assertDoesNotThrow(() -> employeeRepository.search(new HashMap<>()));

        assertEquals(5, employeeEntities.size());
    }

    @Test
    @Transactional
    public void shouldSearchSet() {
        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("name", "Arktos");
        parametersMap.put("salary", 50);

        List<EmployeeEntity> employeeEntities = assertDoesNotThrow(() -> employeeRepository.search(parametersMap));

        assertEquals(2, employeeEntities.size());
        assertEquals(createSearchSetResult(), employeeEntities);
    }

    @Test
    @Transactional
    public void shouldSetSpecificEmployee() {
        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("name", "Arktos");
        parametersMap.put("salary", 50);
        parametersMap.put("surname", "Mrozny");
        parametersMap.put("grade", 100);

        List<EmployeeEntity> employeeEntities = assertDoesNotThrow(() -> employeeRepository.search(parametersMap));

        assertEquals(1, employeeEntities.size());
        assertEquals(createEmployee(300, "Arktos", "Mrozny", 100, 50), employeeEntities.iterator().next());
    }

    private List<EmployeeEntity> createSearchSetResult() {
        List<EmployeeEntity> employeeEntities = new ArrayList<>();

        employeeEntities.add(createEmployee(300, "Arktos", "Mrozny", 100, 50));
        employeeEntities.add(createEmployee(400, "Arktos", "Mr", 100, 50));

        return employeeEntities;
    }

    private EmployeeEntity createEmployee(Integer id, String name, String surname, Integer grade, Integer salary) {
        EmployeeEntity employeeEntity = new EmployeeEntity();

        employeeEntity.setId(id);
        employeeEntity.setName(name);
        employeeEntity.setSalary(salary);
        employeeEntity.setGrade(grade);
        employeeEntity.setSurname(surname);

        return employeeEntity;
    }

}
