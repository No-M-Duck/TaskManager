package m_duck.taskmanager.repos;

import m_duck.taskmanager.DTO.TaskView;
import m_duck.taskmanager.model.tables.pojos.Tasks;
import m_duck.taskmanager.model.tables.pojos.Users;

import java.util.List;
import java.util.UUID;

public interface TaskAssigneesRepo {

    boolean addOrUpdateTaskByUser(UUID taskId, String username);

    boolean deleteTaskByUser(UUID taskId, UUID userId);

    List<TaskView> getUsersByTask(UUID taskId);

    List<Tasks> getTasksByUser(UUID userId);

    boolean deleteUserByTask(UUID task_id,String username);
}
