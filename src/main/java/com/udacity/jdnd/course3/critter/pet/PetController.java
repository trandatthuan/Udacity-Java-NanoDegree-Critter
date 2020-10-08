package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    //##
    //## Routes
    //##
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return convertPetToPetDTO(petService.save(petDTO));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) { return convertPetToPetDTO(petService.findById(petId)); }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.findAll()
                .stream()
                .map(this::convertPetToPetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.findPetByCustomerId(ownerId)
                .stream()
                .map(this::convertPetToPetDTO)
                .collect(Collectors.toList());
    }

    //##
    //## Utility functions
    //##
    public PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();

        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());

        return petDTO;
    }
}
