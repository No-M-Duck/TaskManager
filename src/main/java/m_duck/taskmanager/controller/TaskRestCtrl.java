package m_duck.taskmanager.controller;

import m_duck.taskmanager.exception.TaskException;
import m_duck.taskmanager.model.tables.pojos.Tasks;
import m_duck.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskRestCtrl {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Tasks>> getAllTasks() throws TaskException{
        List<Tasks> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tasks> getTaskById(@PathVariable UUID id) throws TaskException{
        Tasks reqTast = taskService.getTaskById(id);
        return new ResponseEntity<>(reqTast,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createTask(@RequestBody Tasks task) throws TaskException{
        taskService.addTask(task);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateTask(Tasks task) throws TaskException{
        taskService.updateTask(task);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTask(UUID id) throws TaskException{
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
