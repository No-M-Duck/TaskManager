package m_duck.taskmanager.service;

import m_duck.taskmanager.exception.UserRoleException;
import m_duck.taskmanager.model.tables.pojos.Roles;
import m_duck.taskmanager.model.tables.pojos.Users;
import m_duck.taskmanager.repos.JooqUserRolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {

    private final JooqUserRolesRepo userRolesRepo;

    @Autowired
    public UserRoleService(JooqUserRolesRepo jooqUserRolesRepo) {
        this.userRolesRepo = jooqUserRolesRepo;
    }

    public void addRoleForUser(Roles role, Users user) throws UserRoleException {
        boolean isAdded = userRolesRepo.addOrUpdateRoleForUser(user.getUsername(), role.getName());
        if (!isAdded) {
            throw new UserRoleException("Unable to add or update role to current user");
        }
    }

    public void deleteRoleForUser(Roles role, Users user) throws UserRoleException {
        boolean isAdded = userRolesRepo.deleteRoleForUser(user.getId(), role.getId());
        if (!isAdded) {
            throw new UserRoleException("Unable to delete role to current user");
        }
    }

    public List<Roles> getAllRolesByUser(Users user) throws UserRoleException {
        return Optional.ofNullable(
                userRolesRepo.allRolesOfUser(user.getUsername())
        ).orElseThrow(
                () -> new UserRoleException("Roles not found for current user")
        );
    }

    public List<Users> getAllUsersByRoles(Roles role) throws UserRoleException {
        return Optional.ofNullable(
                userRolesRepo.allUsersOfRoles(role.getName())
        ).orElseThrow(
                ()-> new UserRoleException("Users not found with current role")
        );
    }
}
