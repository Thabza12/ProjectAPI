package Vico.ProjectAPI.services;

import Vico.ProjectAPI.domain.User;
import Vico.ProjectAPI.exceptions.UsernameAlreadyExistsException;
import Vico.ProjectAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser (User newUser){

        try{
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            return userRepository.save(newUser);
        }catch (Exception e){

            throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists");

        }



    }
}
