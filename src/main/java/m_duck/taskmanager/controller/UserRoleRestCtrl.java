package m_duck.taskmanager.controller;

import m_duck.taskmanager.DTO.RoleAssignmentRequest;
import m_duck.taskmanager.exception.UserRoleException;
import m_duck.taskmanager.model.tables.pojos.Roles;
import m_duck.taskmanager.model.tables.pojos.Users;
import m_duck.taskmanager.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-role")
public class UserRoleRestCtrl {

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/by-user")
    public ResponseEntity<List<Roles>> getAllRolesByUser(@RequestBody Users user) throws UserRoleException{
        List<Roles> roles = userRoleService.getAllRolesByUser(user);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/by-role")
    public ResponseEntity<List<Users>> getAllUsersByRole(@RequestBody Roles role) throws UserRoleException{
        List<Users> users = userRoleService.getAllUsersByRoles(role);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addRole
            (@RequestBody RoleAssignmentRequest request) throws UserRoleException{
        userRoleService.addRoleForUser(request.getRole(),request.getUser()) ;
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteRole
            (@RequestBody RoleAssignmentRequest request) throws UserRoleException{
        userRoleService.addRoleForUser(request.getRole(),request.getUser());
        return ResponseEntity.noContent().build();
    }
}
