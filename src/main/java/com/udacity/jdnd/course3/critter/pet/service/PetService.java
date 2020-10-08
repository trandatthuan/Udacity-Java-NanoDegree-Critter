package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    /*
        Save pet
     */
    public Pet save(PetDTO petDTO) {
        Pet pet = convertPetDTOToPet(petDTO);

        // Find valid owner
        Optional<Customer> owner = customerRepository.findById(petDTO.getOwnerId());

        if (owner.isEmpty()) {
            throw new EntityNotFoundException("Owner not found.");
        } else {
            pet.setCustomer(owner.get());
            Pet newPet = petRepository.save(pet);

            owner.get().getPets().add(newPet);
            customerRepository.save(owner.get());

            return newPet;
        }
    }

    /*
        Find all pets
     */
    public List<Pet> findAll() { return petRepository.findAll(); }

    /*
        Find pet by id
     */
    public Pet findById(Long id) {
        Optional<Pet> pet = petRepository.findById(id);

        if (pet.isEmpty()) {
            throw new EntityNotFoundException("Invalid pet id.");
        } else {
            return pet.get();
        }
    }

    /*
        Find pet by customer id
     */
    public List<Pet> findPetByCustomerId(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            throw new EntityNotFoundException("Invalid owner id.");
        } else {
            return petRepository.findPetByCustomerId(customerId);
        }
    }

    //##
    //## Utilities Functions
    //##
    public Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();

        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }
}
