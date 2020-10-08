package com.udacity.jdnd.course3.critter.pet.entity;

import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pet {
    //##
    //## Fields
    //##
    @Id
    @GeneratedValue
    private Long id;

    private PetType type;

    @Nationalized
    private String name;

    private LocalDateTime birthDate;

    private String notes;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "Pet_Schedule",
            joinColumns = { @JoinColumn(name = "pet_id") },
            inverseJoinColumns = { @JoinColumn(name = "schedule_id") }
    )
    private List<Schedule> schedules = new ArrayList<>();

    //##
    //## Constructors
    //##
    public Pet() { }

    public Pet(Long id, PetType type, String name, LocalDateTime birthDate, String notes, Customer customer, List<Schedule> schedules) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.birthDate = birthDate;
        this.notes = notes;
        this.customer = customer;
        this.schedules = schedules;
    }

    public Pet(PetType type, String name, LocalDateTime birthDate, String notes) {
        this.type = type;
        this.name = name;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    //##
    //## Getters and setters
    //##

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public PetType getType() { return type; }

    public void setType(PetType type) { this.type = type; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public LocalDateTime getBirthDate() { return birthDate; }

    public void setBirthDate(LocalDateTime birthDate) { this.birthDate = birthDate; }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }

    public Customer getCustomer() { return customer; }

    public void setCustomer(Customer customer) { this.customer = customer; }

    public List<Schedule> getSchedules() { return schedules; }

    public void setSchedule(List<Schedule> schedules) { this.schedules = schedules; }
}
