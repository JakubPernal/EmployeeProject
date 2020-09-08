package com.pernal.controller;

import com.pernal.model.Employee;
import com.pernal.model.EmployeeReq;
import com.pernal.model.EmployeeSearchReq;
import com.pernal.model.EmployeeServiceResponse;
import com.pernal.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ApiOperation(value = "Get employee by id", notes = "This method is used to get employee by provided id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Getting employee processed"),
            @ApiResponse(code = 500, message = "Error while getting employee")})
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeServiceResponse<Employee>> get(@PathVariable Long id) {
        return employeeService.get(id);
    }

    @ApiOperation(value = "Search employees by given params", notes = "This method is used to search employees by given params")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Searching employees processed"),
            @ApiResponse(code = 500, message = "Error while searching employees")})
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeServiceResponse<List<Employee>>> search(@RequestBody EmployeeSearchReq employeeSearchReq) {
        return employeeService.search(employeeSearchReq);
    }

    @ApiOperation(value = "Delete employee by id", notes = "This method is used to delete employee by provided id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleting employee processed"),
            @ApiResponse(code = 500, message = "Error while deleting employee")})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<EmployeeServiceResponse<Void>> delete(@PathVariable Long id) {
        return employeeService.delete(id);
    }

    @ApiOperation(value = "Add employee", notes = "This method is used to add employee by provided request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Adding employee processed"),
            @ApiResponse(code = 500, message = "Error while adding employee")})
    @PostMapping(value = "/insert")
    public ResponseEntity<EmployeeServiceResponse<Void>> add(@RequestBody @Valid EmployeeReq employeeReq) {
        return employeeService.insert(employeeReq);
    }

    @ApiOperation(value = "Update employee by id", notes = "This method is used to update employee by provided id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updating employee processed"),
            @ApiResponse(code = 500, message = "Error while updating employee")})
    @PutMapping(value = "/modify/{id}")
    public ResponseEntity<EmployeeServiceResponse<Void>> update(@PathVariable Long id, @RequestBody @Valid EmployeeReq employeeReq) {
        return employeeService.update(id, employeeReq);
    }
}
