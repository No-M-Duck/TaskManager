package m_duck.taskmanager.repos;

import m_duck.taskmanager.model.tables.pojos.Roles;

import java.util.List;
import java.util.UUID;

public interface RoleRepo {

    boolean saveRole(Roles role);
    List<Roles> getAllRoles();
    Roles getRoleByName(String roleName);

    Roles getRoleById(UUID id);
    boolean updateRole(UUID id,Roles role);
    boolean deleteRole(UUID id);
}
