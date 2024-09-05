package m_duck.taskmanager.repos;

import m_duck.taskmanager.model.Tables;
import m_duck.taskmanager.model.tables.pojos.TaskStatuses;
import m_duck.taskmanager.model.tables.pojos.Tasks;
import m_duck.taskmanager.model.tables.records.TasksRecord;
import org.jooq.DSLContext;
import org.jooq.UpdateSetFirstStep;
import org.jooq.UpdateSetMoreStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class JooqTaskRepo implements TaskRepo{

    private final DSLContext dslContext;

    @Autowired
    public JooqTaskRepo(DSLContext dslContext){
        this.dslContext = dslContext;
    }

    @Override
    public boolean saveTask(Tasks task) {
        UUID status_id = dslContext.selectFrom(Tables.TASK_STATUSES).where(Tables.TASK_STATUSES.NAME.eq("NEW")).fetchOneInto(TaskStatuses.class).getId();
        task.setStatus(status_id);
        LocalDateTime localDateTime = LocalDateTime.now();
        task.setCreatedAt(localDateTime);
        task.setUpdatedAt(localDateTime);
        return dslContext.insertInto(Tables.TASKS)
                .set(dslContext.newRecord(Tables.TASKS,task))
                .onDuplicateKeyIgnore()
                .execute()==1;
    }

    @Override
    public List<Tasks> getAllTasks() {
        return dslContext.selectFrom(Tables.TASKS)
                .fetchInto(Tasks.class);
    }

    @Override
    public Tasks getTaskById(UUID id) {
        return dslContext.selectFrom(Tables.TASKS)
                .where(Tables.TASKS.ID.eq(id))
                .fetchOneInto(Tasks.class);
    }

    @Override
    public boolean updateTask(UUID id,Tasks task) {
        LocalDateTime localDateTime = LocalDateTime.now();
        task.setUpdatedAt(localDateTime);
        task = getTasks(id,task);
        return dslContext.update(Tables.TASKS)
                .set(dslContext.newRecord(Tables.TASKS,task))
                .where(Tables.TASKS.ID.eq(id))
                .execute()==1;
    }

    @Override
    public boolean deleteTask(UUID id) {
        return dslContext.delete(Tables.TASKS)
                .where(Tables.TASKS.ID.eq(id))
                .execute()==1;
    }

    private Tasks getTasks(UUID id,Tasks task){
        Tasks oldTask = getTaskById(id);
        if(task.getTitle()==null){
            task.setTitle(oldTask.getTitle());
        }
        if(task.getDescription()==null){
            task.setDescription(oldTask.getDescription());
        }
        if(task.getStatus()==null){
            task.setStatus(oldTask.getStatus());
        }
        if(task.getCreatedAt()==null){
            task.setCreatedAt(oldTask.getCreatedAt());
        }
        return task;
    }
}
