package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

}
