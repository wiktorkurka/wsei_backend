package wsei.backend.lab2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import wsei.backend.lab2.database.UserRepository;
import wsei.backend.lab2.database.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@RestController
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    public ResponseEntity<HttpStatus> CreateUser(@RequestBody UserEntity userEntity) {
        userRepository.save(userEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    public Object GetUsers() {
        return (List<UserEntity>) userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Optional<UserEntity>> GetUser(@PathVariable Long id) {
        return new ResponseEntity<>(userRepository.findById(id), HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity<UserEntity> CreatUser(@RequestBody UserEntity userEntity) {
        UserEntity user = userRepository.findById(userEntity.getUserId()).get();

        user.setUsername(userEntity.getUsername());
        user.setEmail(userEntity.getEmail());

        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> DeleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
