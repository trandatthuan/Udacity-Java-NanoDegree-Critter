package com.udacity.jdnd.course3.critter.user.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    //##
    //## Fields
    //##
    @Id
    @GeneratedValue
    private Long id;

    @Nationalized
    private String name;

    //##
    //## Constructors
    //##
    public User() { }

    public User(String name) {
        this.name = name;
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    //##
    //## Getters and setters
    //##
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
