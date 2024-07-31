package m_duck.taskmanager.service;

import m_duck.taskmanager.exception.StatusException;
import m_duck.taskmanager.model.tables.pojos.TaskStatuses;
import m_duck.taskmanager.repos.JooqStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    private final JooqStatusRepo statusRepo;

    @Autowired
    public StatusService(JooqStatusRepo statusRepo){
        this.statusRepo = statusRepo;
    }

    public void addStatus(TaskStatuses status) throws StatusException {
        boolean isAdded = statusRepo.createStatus(status);
        if(!isAdded){
            throw new StatusException("Status is not added");
        }
    }

    public List<TaskStatuses> getAllStatus() throws StatusException{
        List<TaskStatuses> statuses = statusRepo.getAllStatus();
        if(statuses.isEmpty()){
            throw new StatusException("Statuses not found");
        }
        return statuses;
    }

    public TaskStatuses getStatusByName(TaskStatuses status) throws StatusException{
        return Optional.ofNullable(statusRepo.getStatusByName(status.getName()))
                .orElseThrow(
                        ()-> new StatusException("Current status not found")
                );

    }

    public void updateStatus(TaskStatuses status) throws StatusException {
        boolean isUpdate = statusRepo.createStatus(status);
        if(!isUpdate){
            throw new StatusException("Status is not updated");
        }
    }

    public void deleteStatus(TaskStatuses status) throws StatusException {
        boolean isDeleted = statusRepo.createStatus(status);
        if(!isDeleted){
            throw new StatusException("Status is not deleted");
        }
    }
}
