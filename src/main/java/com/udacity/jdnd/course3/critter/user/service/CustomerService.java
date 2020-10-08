package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;

    /*
        Find all customers
     */
    public List<Customer> findAll() { return customerRepository.findAll(); }

    /*
        Save customer
     */
    public Customer save(CustomerDTO customerDTO) {
        Customer customer = convertCustomerDTOToCustomer(customerDTO);
        return customerRepository.save(customer);
    }

    /*
        Find customer by pet id
     */
    public Customer findCustomerByPetId(Long petId) {
        Optional<Pet> pet = petRepository.findById(petId);

        if (pet.isEmpty()) {
            throw new EntityNotFoundException("Invalid pet id.");
        } else {
            return customerRepository.findByPetsId(petId);
        }
    }

    //##
    //## Utility Functions
    //##
    public Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();

        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }
}
