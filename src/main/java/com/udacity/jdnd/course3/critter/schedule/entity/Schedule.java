package com.udacity.jdnd.course3.critter.schedule.entity;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.entity.Employee;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NamedQueries({
    @NamedQuery(
        name = "Schedule.findAllSchedules",
        query = "select sched from Schedule sched"),
    @NamedQuery(
        name = "Schedule.findSchedulesByPetId",
        query = "select sched from Schedule sched " +
                "inner join fetch sched.pets p " +
                "where p.id = :petId"),
    @NamedQuery(
        name = "Schedule.findSchedulesByCustomerId",
        query = "select sched from Schedule sched " +
                "inner join fetch sched.pets p " +
                "where p.customer.id = :customerId"),
    @NamedQuery(
        name = "Schedule.findSchedulesByEmployeeId",
        query = "select sched from Schedule sched " +
                "inner join fetch sched.employees emply " +
                "where emply.id = :employeeId")
})
@Entity
public class Schedule {
    //##
    //## Fields
    //##
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "schedules", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "schedules", cascade = CascadeType.ALL)
    private List<Pet> pets = new ArrayList<>();

    private LocalDate date;

    @ElementCollection
    private Set<EmployeeSkill> activities;

    //##
    //## Constructors
    //##
    public Schedule() { }

    public Schedule(Long id, List<Employee> employees, List<Pet> pets, LocalDate date, Set<EmployeeSkill> activities) {
        this.id = id;
        this.employees = employees;
        this.pets = pets;
        this.date = date;
        this.activities = activities;
    }

    public Schedule(List<Employee> employees, List<Pet> pets, LocalDate date, Set<EmployeeSkill> activities) {
        this.employees = employees;
        this.pets = pets;
        this.date = date;
        this.activities = activities;
    }

    //##
    //## Getters and setters
    //##
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public List<Employee> getEmployees() { return employees; }

    public void setEmployees(List<Employee> employees) { this.employees = employees; }

    public List<Pet> getPets() { return pets; }

    public void setPets(List<Pet> pets) { this.pets = pets; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public Set<EmployeeSkill> getActivities() { return activities; }

    public void setActivities(Set<EmployeeSkill> activities) { this.activities = activities; }
}
