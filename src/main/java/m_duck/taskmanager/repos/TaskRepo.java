package m_duck.taskmanager.repos;

import m_duck.taskmanager.model.tables.pojos.Tasks;

import java.util.List;
import java.util.UUID;

public interface TaskRepo {

    boolean saveTask(Tasks task);

    List<Tasks> getAllTasks();

    Tasks getTaskById(UUID id);

    boolean updateTask(UUID id,Tasks task);

    boolean deleteTask(UUID id);
}
