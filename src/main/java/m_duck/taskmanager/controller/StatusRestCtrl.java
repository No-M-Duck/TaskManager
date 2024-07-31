package m_duck.taskmanager.controller;

import m_duck.taskmanager.exception.StatusException;
import m_duck.taskmanager.model.tables.pojos.TaskStatuses;
import m_duck.taskmanager.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<TaskStatuses> getStatusById(@RequestBody TaskStatuses status) throws StatusException{
        TaskStatuses reqStatus = statusService.getStatusByName(status);
        return new ResponseEntity<>(reqStatus,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createStatus(@RequestBody TaskStatuses status) throws StatusException{
        statusService.addStatus(status);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateStatus(@RequestBody TaskStatuses status) throws StatusException{
        statusService.updateStatus(status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteStatus(@RequestBody TaskStatuses status) throws  StatusException{
        statusService.deleteStatus(status);
        return ResponseEntity.noContent().build();
    }
}
