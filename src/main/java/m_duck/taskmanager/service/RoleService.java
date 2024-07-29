package m_duck.taskmanager.service;

import ch.qos.logback.core.rolling.RolloverFailure;
import m_duck.taskmanager.exception.RoleException;
import m_duck.taskmanager.model.Tables;
import m_duck.taskmanager.repos.JooqRoleRepo;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import m_duck.taskmanager.model.tables.pojos.Roles;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {


     JooqRoleRepo jooqRoleRepo;

     @Autowired
     public RoleService(JooqRoleRepo jooqRoleRepo){
          this.jooqRoleRepo=jooqRoleRepo;
     }

     public void addNewRole(Roles role) throws  RoleException {
          boolean isAdded = jooqRoleRepo.saveRole(role);
          if(!isAdded){
              throw  new RoleException("Role is not added");
          }

     }

     public List<Roles> getRoles() throws RoleException{
         List<Roles> roles = jooqRoleRepo.getAllRoles();
         if(roles.isEmpty()) throw new RoleException("Roles not found");
         return roles;
     }

     public Roles getRoleByName(String roleName) throws RoleException{
          return Optional.ofNullable(jooqRoleRepo.getRoleByName(roleName)).orElseThrow(
                  ()-> new RoleException("Role not found")
          );
     }

     public void updateRole(Roles role) throws RoleException{
          boolean isUpdate = jooqRoleRepo.updateRole(role);
          if(!isUpdate){
               throw  new RoleException("Role is not updated");
          }

     }

     public void deleteRole(Roles role)throws RoleException{
         boolean isDelete = jooqRoleRepo.deleteRole(role);
         if(!isDelete){
              throw new RoleException("Role is not deleted");
         }

     }
}
