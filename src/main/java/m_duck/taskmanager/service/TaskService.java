package m_duck.taskmanager.service;

import m_duck.taskmanager.exception.TaskException;
import m_duck.taskmanager.model.tables.pojos.Tasks;
import m_duck.taskmanager.repos.JooqTaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private final JooqTaskRepo taskRepo;

    @Autowired
    public TaskService (JooqTaskRepo jooqTaskRepo){
        this.taskRepo = jooqTaskRepo;
    }

    public List<Tasks> getAllTasks() throws TaskException{
        List<Tasks> tasks = taskRepo.getAllTasks();
        return Optional.ofNullable(tasks).orElseThrow(
                ()-> new TaskException("Tasks not found")
        );
    }

    public Tasks getTaskById(UUID id) throws TaskException{
        Tasks task = taskRepo.getTaskById(id);
        return Optional.ofNullable(task).orElseThrow(
                ()-> new TaskException("Task not found with current ID")
        );
    }

    public void addTask (Tasks task) throws TaskException{
        boolean isAdded = taskRepo.saveTask(task);
        if(!isAdded){
            throw new TaskException("Task is not added");
        }
    }

    public void updateTask(UUID id,Tasks task) throws TaskException{
        boolean isUpdate = taskRepo.updateTask(id,task);
        if(!isUpdate){
            throw new TaskException("Task is not updated with id:" + task.getId());
        }
    }

    public void deleteTask(UUID id) throws TaskException{
        boolean isDelete = taskRepo.deleteTask(id);
        if(!isDelete){
            throw new TaskException("Task is not deleted with ID:"+id);
        }
    }
}
