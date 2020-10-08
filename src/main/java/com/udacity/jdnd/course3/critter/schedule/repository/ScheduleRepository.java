package com.udacity.jdnd.course3.critter.schedule.repository;

import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ScheduleRepository {
    @PersistenceContext
    EntityManager entityManager;

    public void persist(Schedule schedule) { entityManager.persist(schedule); }

    public Schedule find(Long id) { return entityManager.find(Schedule.class, id); }

    public Schedule merge(Schedule detachedSchedule) { return entityManager.merge(detachedSchedule); }

    /*
        Get all schedules
     */
    public List<Schedule> findAllSchedules() {
        TypedQuery<Schedule> query = entityManager.createNamedQuery("Schedule.findAllSchedules", Schedule.class);
        return query.getResultList();
    }

    /*
        Find schedule by pet
    */
    public List<Schedule> findSchedulesByPetId(Long petId) {
        try {
            TypedQuery<Schedule> query = entityManager.createNamedQuery("Schedule.findSchedulesByPetId", Schedule.class);
            query.setParameter("petId", petId);
            return query.getResultList();
        } catch (Exception ex) {
            throw new EntityNotFoundException("Invalid pet id.");
        }
    }

    /*
        Find schedule by customer
    */
    public List<Schedule> findSchedulesByCustomerId(Long customerId) {
        try {
            TypedQuery<Schedule> query = entityManager.createNamedQuery("Schedule.findSchedulesByCustomerId", Schedule.class);
            query.setParameter("customerId", customerId);
            return query.getResultList();
        } catch(Exception ex) {
            throw new EntityNotFoundException("Invalid pet id.");
        }
    }

    /*
        Find schedule by employee
    */
    public List<Schedule> findSchedulesByEmployeeId(Long employeeId) {
        try {
            TypedQuery<Schedule> query = entityManager.createNamedQuery("Schedule.findSchedulesByEmployeeId", Schedule.class);
            query.setParameter("employeeId", employeeId);
            return query.getResultList();
        } catch(Exception ex) {
            throw new EntityNotFoundException("Invalid employee id.");
        }
    }
}
