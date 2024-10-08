package m_duck.taskmanager.controller;

import m_duck.taskmanager.exception.UserException;
import m_duck.taskmanager.model.tables.pojos.Users;
import m_duck.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/users")
public class UserRestCtrl {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() throws UserException {
        List<Users> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
        System.out.println(username);
        boolean exists = userService.checkUsername(username);
        return ResponseEntity.ok(exists);
    }
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        System.out.println(email);
        boolean exists = userService.checkEmail( email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUser(@PathVariable UUID id) throws UserException {
        Users retUser = userService.getUserById(id);
        return new ResponseEntity<>(retUser, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody Users user) throws UserException {
        userService.addNewUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> usersResponseEntity(@PathVariable UUID id,@RequestBody Users user) throws UserException{
        userService.updateUser(id,user);

        return  ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletUser(@PathVariable UUID id) throws UserException{
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
