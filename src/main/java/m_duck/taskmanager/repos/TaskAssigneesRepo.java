package m_duck.taskmanager.repos;

import m_duck.taskmanager.model.tables.pojos.Tasks;
import m_duck.taskmanager.model.tables.pojos.Users;

import java.util.List;
import java.util.UUID;

public interface TaskAssigneesRepo {

    boolean addOrUpdateTaskByUser(UUID taskId, UUID userId);

    boolean deleteTaskByUser(UUID taskId, UUID userId);

    List<Users> getUsersByTask(UUID taskId);

    List<Tasks> getTasksByUser(UUID userId);
}
