package m_duck.taskmanager.repos;

import m_duck.taskmanager.model.tables.pojos.Users;

import java.util.List;
import java.util.UUID;

public interface UserRepo {

    boolean saveUser(Users user);

    List<Users> getAllUsers();

    Users getUserByUserNameOrEmail(String value);

    Users getUserById(UUID id);

    boolean updateUser(UUID id, Users user);

    boolean deleteUser(UUID id);

}
