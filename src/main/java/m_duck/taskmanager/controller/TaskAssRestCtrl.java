package m_duck.taskmanager.controller;

import m_duck.taskmanager.DTO.TaskAssigneesRequest;
import m_duck.taskmanager.DTO.TaskView;
import m_duck.taskmanager.exception.TaskAssException;
import m_duck.taskmanager.model.tables.pojos.Tasks;
import m_duck.taskmanager.model.tables.pojos.Users;
import m_duck.taskmanager.service.TaskAssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task-assignees")
public class TaskAssRestCtrl {

    @Autowired
    private TaskAssService taskAssService;

    @GetMapping("/by-user/{id}")
    public ResponseEntity<List<Tasks>>  getAllTasksByUser(@PathVariable UUID id) throws TaskAssException{
        List<Tasks> tasks = taskAssService.getAllTasksByUser(id);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/by-task/{id}")
    public ResponseEntity<List<TaskView>> getUsersByTask(@PathVariable UUID id) throws TaskAssException{
        List<TaskView> users = taskAssService.getAllUsersByTask(id);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    @GetMapping("/free-user")
    public ResponseEntity<List<TaskView>> getFreeUsers() throws TaskAssException{
        List<TaskView> users = taskAssService.getFreeUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping("/full-users/{task}")
    public ResponseEntity<List<TaskView>> getFreeUsersAndUsersWithOtherTasks(@PathVariable UUID task) throws TaskAssException{
        List<TaskView> users = taskAssService.getFullUserWithoutCurrentTask(task);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addUser
            (@RequestBody TaskAssigneesRequest request) throws TaskAssException{
        taskAssService.addTaskByUser(request.getTask(),request.getUser());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/by-user")
    public ResponseEntity<Void> deleteUser
            (@RequestBody TaskAssigneesRequest request) throws TaskAssException{
        taskAssService.deleteUserByTask(request.getTask().getId(),request.getUser().getUsername());
        return ResponseEntity.noContent().build();
    }
}
