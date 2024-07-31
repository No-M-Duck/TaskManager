package m_duck.taskmanager.repos;

import m_duck.taskmanager.model.tables.pojos.TaskStatuses;

import java.util.List;
import java.util.UUID;

public interface StatusRepo {

    boolean createStatus(TaskStatuses status);

    List<TaskStatuses> getAllStatus();

    TaskStatuses getStatusByName(String name);

    boolean updateStatus(TaskStatuses status);

    boolean deleteStatus(TaskStatuses status);
}
