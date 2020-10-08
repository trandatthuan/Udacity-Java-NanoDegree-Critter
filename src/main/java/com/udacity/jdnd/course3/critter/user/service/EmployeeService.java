package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    /*
        Find one employee by id
     */
    public Employee findOne(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isEmpty()) {
            throw new EntityNotFoundException("Cannot find employee id " + id);
        } else {
            return employeeRepository.getOne(id);
        }
    }

    /*
        Save employee
     */
    public Employee save(EmployeeDTO employeeDTO) {
        Employee employee = convertEmployeeDTOToEmployee(employeeDTO);
        return employeeRepository.save(employee);
    }

    /*
        Update employee's available day of week
     */
    public Employee updateEmployeeDaysAvailable(Set<DayOfWeek> daysAvailable, Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isEmpty()) {
            throw new EntityNotFoundException("Cannot find employee id " + id);
        } else {
            employee.get().setDaysAvailable(daysAvailable);
        }

        return employeeRepository.save(employee.get());
    }

    /*
        Find available employee by available day of week and skill sets
     */
    public List<Employee> findEmpByAvDayOfWeekAndSkills(EmployeeRequestDTO employeeRequestDTO) {
        List<Employee> employees = employeeRepository.findByDaysAvailable(employeeRequestDTO.getDate().getDayOfWeek());

        employees = employees
                .stream()
                .filter(employee -> employee.getSkills().containsAll(employeeRequestDTO.getSkills()))
                .collect(Collectors.toList());
        return employees;
    }

    //##
    //## Utility Functions
    //##
    public Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    public EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    public Employee convertEmployeeRequestDTOToEmployee(EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = new Employee();

        BeanUtils.copyProperties(employeeRequestDTO, employee);
        return employee;
    }
}
