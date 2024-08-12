package m_duck.taskmanager.repos;

import m_duck.taskmanager.model.Tables;
import m_duck.taskmanager.model.tables.pojos.TaskStatuses;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JooqStatusRepo implements StatusRepo{

    private final DSLContext dslContext;

    @Autowired
    public JooqStatusRepo(DSLContext dslContext){
        this.dslContext = dslContext;
    }
    @Override
    public boolean createStatus(TaskStatuses status) {
        status.setName(status.getName().toUpperCase());
        return dslContext.insertInto(Tables.TASK_STATUSES)
                .set(dslContext.newRecord(Tables.TASK_STATUSES,status))
                .onDuplicateKeyIgnore()
                .execute()==1;
    }

    @Override
    public List<TaskStatuses> getAllStatus() {
        return dslContext.selectFrom(Tables.TASK_STATUSES).fetchInto(TaskStatuses.class);
    }

    @Override
    public TaskStatuses getStatusByName(String name) {
        return dslContext.selectFrom(Tables.TASK_STATUSES)
                .where(Tables.TASK_STATUSES.NAME.eq(name))
                .fetchOneInto(TaskStatuses.class);
    }

    @Override
    public TaskStatuses getStatusById(UUID id) {
        return dslContext.selectFrom(Tables.TASK_STATUSES)
                .where(Tables.TASK_STATUSES.ID.eq(id))
                .fetchOneInto(TaskStatuses.class);
    }

    @Override
    public boolean updateStatus(UUID id,TaskStatuses status) {
        status.setName(status.getName().toUpperCase());
        return dslContext.update(Tables.TASK_STATUSES)
                .set(Tables.TASK_STATUSES.NAME,status.getName())
                .where(Tables.TASK_STATUSES.ID.eq(id))
                .execute()==1;
    }

    @Override
    public boolean deleteStatus(UUID id) {
        return dslContext.delete(Tables.TASK_STATUSES)
                .where(Tables.TASK_STATUSES.ID.eq(id))
                .execute()==1;
    }
}
