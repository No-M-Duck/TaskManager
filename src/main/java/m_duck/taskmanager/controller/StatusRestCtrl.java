package m_duck.taskmanager.controller;

import m_duck.taskmanager.exception.StatusException;
import m_duck.taskmanager.model.tables.pojos.TaskStatuses;
import m_duck.taskmanager.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/status")
public class StatusRestCtrl {

    @Autowired
    private StatusService statusService;

    @GetMapping
    public ResponseEntity<List<TaskStatuses>> getAllStatus() throws StatusException {
        List<TaskStatuses> statuses = statusService.getAllStatus();
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskStatuses> getStatusById(@PathVariable UUID id) throws StatusException{
        TaskStatuses reqStatus = statusService.getStatusById(id);
        return new ResponseEntity<>(reqStatus,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createStatus(@RequestBody TaskStatuses status) throws StatusException{
        statusService.addStatus(status);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable UUID id,@RequestBody TaskStatuses status) throws StatusException{
        statusService.updateStatus(id,status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable UUID id) throws  StatusException{
        statusService.deleteStatus(id);
        return ResponseEntity.noContent().build();
    }
}
