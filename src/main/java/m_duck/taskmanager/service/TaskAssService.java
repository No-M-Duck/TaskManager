package m_duck.taskmanager.service;

import m_duck.taskmanager.DTO.TaskView;
import m_duck.taskmanager.exception.TaskAssException;
import m_duck.taskmanager.model.tables.pojos.Tasks;
import m_duck.taskmanager.model.tables.pojos.Users;
import m_duck.taskmanager.repos.JooqRoleRepo;
import m_duck.taskmanager.repos.JooqTaskAssRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskAssService {

    private final JooqTaskAssRepo taskAssRepo;
    private final JooqRoleRepo roleRepo;

    @Autowired
    public TaskAssService(JooqTaskAssRepo taskAssRepo, JooqRoleRepo roleRepo) {
        this.taskAssRepo = taskAssRepo;
        this.roleRepo = roleRepo;
    }

    public void addTaskByUser(Tasks task, Users user) throws TaskAssException {
        boolean isAddded = taskAssRepo.addOrUpdateTaskByUser(task.getId(), user.getUsername());
        if (!isAddded) {
            throw new TaskAssException("Unable to add or update task to current user");
        }
    }

    public void deleteTaskByUser(Tasks task, Users user) throws TaskAssException {
        boolean isDeleted = taskAssRepo.deleteTaskByUser(task.getId(), user.getId());
        if (!isDeleted) {
            throw new TaskAssException("Unable to delete task to current user");
        }
    }

    public List<Tasks> getAllTasksByUser(UUID uuid) throws TaskAssException {
        return Optional.ofNullable(taskAssRepo.getTasksByUser(uuid))
                .orElseThrow(
                        () -> new TaskAssException("Tasks not found for current user")
                );
    }

    public List<TaskView> getAllUsersByTask(UUID uuid) throws TaskAssException {
        return Optional.ofNullable(taskAssRepo.getUsersByTask(uuid))
                .orElseThrow(
                        () -> new TaskAssException("Users not found for current task")
                );
    }

    public void deleteUserByTask(UUID task_id,String username) throws TaskAssException{
        boolean isDeleted = taskAssRepo.deleteUserByTask(task_id,username);
        if (!isDeleted) {
            throw new TaskAssException("Unable to delete user to current task");
        }
    }

    public List<TaskView> getFreeUsers() throws TaskAssException{
        return Optional.ofNullable(taskAssRepo.getUsersWithoutTask())
                .orElseThrow(
                        ()->new TaskAssException("Users without task not found")
                );
    }

    public List<TaskView> getFullUserWithoutCurrentTask(UUID task_id) throws TaskAssException{
        return Optional.ofNullable(taskAssRepo.getUsersWithOtherTaskOrWithoutTask(task_id))
                .orElseThrow(
                        ()-> new TaskAssException("Error of search free user or user with other tasks")
                );
    }
}
