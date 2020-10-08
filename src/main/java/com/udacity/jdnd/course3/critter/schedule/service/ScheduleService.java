package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;

    /*
        Insert new schedule to db and return it
     */
    public Schedule createSchedule(ScheduleDTO scheduleDTO) {
        Schedule newSchedule = convertScheduleDTOToSchedule(scheduleDTO);

        // Save employee(s) into schedule
        for (Long employeeId : scheduleDTO.getEmployeeIds()) {
            Optional<Employee> employee = employeeRepository.findById(employeeId);

            if (employee.isEmpty()) {
                throw new EntityNotFoundException("Invalid employee id");
            } else {
                newSchedule.getEmployees().add(employee.get());
            }
        }

        // Save pet(s) into schedule
        for (Long petId : scheduleDTO.getPetIds()) {
            Optional<Pet> pet = petRepository.findById(petId);

            if (pet.isEmpty()) {
                throw new EntityNotFoundException("Invalid pet id");
            } else {
                newSchedule.getPets().add(pet.get());
            }
        }

        scheduleRepository.persist(newSchedule);
        newSchedule = scheduleRepository.find(newSchedule.getId());

        // Save schedule to employee(s)
        for (Long employeeId : scheduleDTO.getEmployeeIds()) {
            Employee employee = employeeRepository.getOne(employeeId);

            employee.getSchedules().add(newSchedule);
            employeeRepository.save(employee);
        }

        // Save schedule to pet(s)
        for (Long petId : scheduleDTO.getPetIds()) {
            Pet pet = petRepository.getOne(petId);

            pet.getSchedules().add(newSchedule);
            petRepository.save(pet);
        }

        return newSchedule;
    }

    /*
        Return all schedules
     */
    public List<Schedule> fetchAllSchedules() { return scheduleRepository.findAllSchedules(); }

    /*
        Find schedules by pet id
    */
    public List<Schedule> findSchedulesByPetId(Long petId) {
        Optional<Pet> pet = petRepository.findById(petId);

        if (pet.isEmpty()) {
            throw new EntityNotFoundException("Invalid pet id.");
        } else {
            return scheduleRepository.findSchedulesByPetId(petId);
        }
    }

    /*
        Find schedules by employee id
    */
    public List<Schedule> findSchedulesByEmployeeId(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);

        if (employee.isEmpty()) {
            throw new EntityNotFoundException("Invalid employee id.");
        } else {
            return scheduleRepository.findSchedulesByEmployeeId(employeeId);
        }
    }

    /*
        Find schedules by customer id
    */
    public List<Schedule> findSchedulesByCustomerId(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            throw new EntityNotFoundException("Invalid customer id.");
        } else {
            return scheduleRepository.findSchedulesByCustomerId(customerId);
        }
    }

    //##
    //## Utility Functions
    //##
    public Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();

        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }
}
