package m_duck.taskmanager.service;

import m_duck.taskmanager.exception.TaskAssException;
import m_duck.taskmanager.model.tables.pojos.Tasks;
import m_duck.taskmanager.model.tables.pojos.Users;
import m_duck.taskmanager.repos.JooqTaskAssRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskAssService {

    private final JooqTaskAssRepo taskAssRepo;

    @Autowired
    public TaskAssService(JooqTaskAssRepo taskAssRepo){
        this.taskAssRepo = taskAssRepo;
    }

    public void addTaskByUser(Tasks task, Users user) throws TaskAssException{
        boolean isAddded = taskAssRepo.addOrUpdateTaskByUser(task.getId(),user.getId());
        if(!isAddded){
            throw new TaskAssException("Unable to add or update task to current user");
        }
    }

    public void deleteTaskByUser(Tasks task, Users user) throws TaskAssException{
        boolean isDeleted = taskAssRepo.deleteTaskByUser(task.getId(),user.getId());
        if(!isDeleted){
            throw new TaskAssException("Unable to delete task to current user");
        }
    }

    public List<Tasks> getAllTasksByUser(Users user) throws TaskAssException{
        return Optional.ofNullable(taskAssRepo.getTasksByUser(user.getId()))
                .orElseThrow(
                        ()-> new TaskAssException("Tasks not found for current user")
                );
    }

    public List<Users> getAllUsersByTask(Tasks task) throws TaskAssException{
        return Optional.ofNullable(taskAssRepo.getUsersByTask(task.getId()))
                .orElseThrow(
                        ()-> new TaskAssException("Users not found for current task")
                );
    }
}
