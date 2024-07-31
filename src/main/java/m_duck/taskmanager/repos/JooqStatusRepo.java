package m_duck.taskmanager.repos;

import m_duck.taskmanager.model.Tables;
import m_duck.taskmanager.model.tables.pojos.TaskStatuses;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class JooqStatusRepo implements StatusRepo{

    private final DSLContext dslContext;

    @Autowired
    public JooqStatusRepo(DSLContext dslContext){
        this.dslContext = dslContext;
    }
    @Override
    public boolean createStatus(TaskStatuses status) {
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
    public boolean updateStatus(TaskStatuses status) {
        return dslContext.update(Tables.TASK_STATUSES)
                .set(Tables.TASK_STATUSES.NAME,status.getName())
                .where(Tables.TASK_STATUSES.ID.eq(status.getId()))
                .execute()==1;
    }

    @Override
    public boolean deleteStatus(TaskStatuses status) {
        return dslContext.delete(Tables.TASK_STATUSES)
                .where(Tables.TASK_STATUSES.ID.eq(status.getId()))
                .execute()==1;
    }
}
