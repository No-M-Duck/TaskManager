package m_duck.taskmanager.repos;

import m_duck.taskmanager.model.tables.pojos.Roles;
import m_duck.taskmanager.model.tables.pojos.Users;

import java.util.List;
import java.util.UUID;

public interface UserRolesRepo {

    boolean addOrUpdateRoleForUser(String username, String role);
    boolean deleteRoleForUser(UUID user_id, UUID role_id);
    List<Roles> allRolesOfUser(String username);
    List<Users> allUsersOfRoles(String roleName);
}
