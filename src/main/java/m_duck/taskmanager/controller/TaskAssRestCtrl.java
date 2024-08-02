package m_duck.taskmanager.controller;

import m_duck.taskmanager.DTO.TaskAssigneesRequest;
import m_duck.taskmanager.exception.TaskAssException;
import m_duck.taskmanager.model.tables.pojos.Tasks;
import m_duck.taskmanager.model.tables.pojos.Users;
import m_duck.taskmanager.service.TaskAssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-assignees")
public class TaskAssRestCtrl {

    @Autowired
    private TaskAssService taskAssService;

    @GetMapping("/by-user")
    public ResponseEntity<List<Tasks>>  getAllTasksByUser(@RequestBody Users user ) throws TaskAssException{
        List<Tasks> tasks = taskAssService.getAllTasksByUser(user);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/by-task")
    public ResponseEntity<List<Users>> getUsersByTask(@RequestBody Tasks task) throws TaskAssException{
        List<Users> users = taskAssService.getAllUsersByTask(task);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @PostMapping("/by-user")
    public ResponseEntity<Void> addUser
            (@RequestBody TaskAssigneesRequest request) throws TaskAssException{
        taskAssService.addTaskByUser(request.getTask(),request.getUser());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/by-user")
    public ResponseEntity<Void> deleteUser
            (@RequestBody TaskAssigneesRequest request) throws TaskAssException{
        taskAssService.deleteTaskByUser(request.getTask(),request.getUser());
        return ResponseEntity.noContent().build();
    }
}
