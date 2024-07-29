package m_duck.taskmanager.repos;

import m_duck.taskmanager.model.tables.pojos.Roles;

import java.util.List;

public interface RoleRepo {

    boolean saveRole(Roles role);
    List<Roles> getAllRoles();
    Roles getRoleByName(String roleName);
    boolean updateRole(Roles role);
    boolean deleteRole(Roles role);
}
