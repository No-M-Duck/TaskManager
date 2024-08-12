package m_duck.taskmanager.repos;

import m_duck.taskmanager.model.Tables;
import m_duck.taskmanager.model.tables.pojos.TaskStatuses;
import m_duck.taskmanager.model.tables.pojos.Tasks;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        UUID task_id = dslContext.selectFrom(Tables.TASK_STATUSES).where(Tables.TASK_STATUSES.NAME.eq("NEW")).fetchOneInto(TaskStatuses.class).getId();
        task.setStatus(task_id);
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
}
