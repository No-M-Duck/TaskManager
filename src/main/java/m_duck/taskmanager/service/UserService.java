package m_duck.taskmanager.service;

import m_duck.taskmanager.exception.UserException;
import m_duck.taskmanager.model.tables.pojos.Users;
import m_duck.taskmanager.repos.JooqUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    JooqUserRepo userRepo;

    @Autowired
    public UserService(JooqUserRepo userRepo){
        this.userRepo = userRepo;
    }

    public void addNewUser(Users user) throws UserException{
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(user.getCreatedAt());
        boolean isAdded = userRepo.saveUser(user);
        if(!isAdded){
            throw new UserException("User is not added");
        }
    }

    public List<Users> getUsers() throws UserException{
        List<Users> users = userRepo.getAllUsers();
        if(users.isEmpty()) throw new UserException("Users not found");
        return users;
    }

    public Users getUserByUsernameOrEmail(String user) throws UserException{
        return Optional.ofNullable(userRepo.getUserByUserNameOrEmail(user))
                .orElseThrow(
                        ()-> new UserException("User not found")
                );

    }
    public void updateUser(UUID uuid, Users user) throws UserException{
        user.setUpdatedAt(LocalDateTime.now());
        boolean isUpdated = userRepo.updateUser(uuid,user);
        if(!isUpdated) throw new UserException("User is not updated");
    }

    public void deleteUser(UUID id) throws UserException{
        boolean isDeleted = userRepo.deleteUser(id);
        if(!isDeleted) throw new UserException("User is not deleted");
    }

    public Users getUserById(UUID id) throws UserException{
        return Optional.ofNullable(userRepo.getUserById(id))
                .orElseThrow(
                        ()-> new UserException("User not found")
                );

    }

    public boolean checkUsername(String username){
        return userRepo.checkUser(username);
    }
    public boolean checkEmail(String email){
        return userRepo.checkEmail(email);
    }


}
