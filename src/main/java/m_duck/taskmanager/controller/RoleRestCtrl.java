package m_duck.taskmanager.controller;

import m_duck.taskmanager.exception.RoleException;
import m_duck.taskmanager.model.tables.pojos.Roles;
import m_duck.taskmanager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
public class RoleRestCtrl {
    @Autowired
    private RoleService roleService;

    //Тестовое создание роли
    /*private void createRole(){
        Roles role = new Roles();
        role.setName("Admin");
        roleService.addNewRole(role);
    }

     */

    @GetMapping
    public ResponseEntity<List<Roles>> getAllRoles() throws RoleException {
        List<Roles> roles = roleService.getRoles();
        return  new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Roles> createRole(@RequestBody Roles role) throws RoleException{
        roleService.addNewRole(role);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Roles> updateRole(@PathVariable UUID id, @RequestBody Roles role) throws RoleException{
        roleService.updateRole(id,role);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteROle(@PathVariable UUID id) throws RoleException{
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }


}
