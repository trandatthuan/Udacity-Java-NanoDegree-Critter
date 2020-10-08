package com.udacity.jdnd.course3.critter.user.entity;

import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Employee extends User {
    //##
    //## Fields
    //##
    @ElementCollection
    private Set<EmployeeSkill> skills;

    @ElementCollection
    private Set<DayOfWeek> daysAvailable;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "Employee_Schedule",
            joinColumns = { @JoinColumn(name = "employee_id") },
            inverseJoinColumns = { @JoinColumn(name = "schedule_id") }
    )
    private List<Schedule> schedules = new ArrayList<>();

    //##
    //## Constructors
    //##
    public Employee() { }

    public Employee(Long id, String name, Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable, List<Schedule> schedules) {
        super(id, name);
        this.skills = skills;
        this.daysAvailable = daysAvailable;
        this.schedules = schedules;
    }

    public Employee(String name, Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable, List<Schedule> schedule) {
        super(name);
        this.skills = skills;
        this.daysAvailable = daysAvailable;
        this.schedules = schedules;
    }

    //##
    //## Getters and setters
    //##
    public Set<EmployeeSkill> getSkills() { return skills; }

    public void setSkills(Set<EmployeeSkill> skills) { this.skills = skills; }

    public Set<DayOfWeek> getDaysAvailable() { return daysAvailable; }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) { this.daysAvailable = daysAvailable; }

    public List<Schedule> getSchedules() { return schedules; }

    public void setSchedule(List<Schedule> schedules) { this.schedules = schedules; }
}
