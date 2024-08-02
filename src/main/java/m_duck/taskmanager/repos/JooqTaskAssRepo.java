package m_duck.taskmanager.repos;

import m_duck.taskmanager.model.Tables;
import m_duck.taskmanager.model.tables.pojos.Tasks;
import m_duck.taskmanager.model.tables.pojos.Users;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public class JooqTaskAssRepo implements TaskAssigneesRepo{

    private final DSLContext dslContext;

    @Autowired
    public JooqTaskAssRepo(DSLContext dslContext){
        this.dslContext = dslContext;
    }


    @Override
    public boolean addOrUpdateTaskByUser(UUID taskId, UUID userId) {
        return dslContext.insertInto(Tables.TASK_ASSIGNEES)
                .set(Tables.TASK_ASSIGNEES.TASK_ID,taskId)
                .set(Tables.TASK_ASSIGNEES.USER_ID,userId)
                .onConflict(Tables.TASK_ASSIGNEES.TASK_ID,Tables.TASK_ASSIGNEES.USER_ID)
                .doUpdate()
                .set(Tables.TASK_ASSIGNEES.TASK_ID,taskId)
                .execute()==1;
    }

    @Override
    public boolean deleteTaskByUser(UUID taskId, UUID userId) {
        return dslContext.delete(Tables.TASK_ASSIGNEES)
                .where(Tables.TASK_ASSIGNEES.TASK_ID.eq(taskId))
                .and(Tables.TASK_ASSIGNEES.USER_ID.eq(userId))
                .execute()==1;
    }

    @Override
    public List<Users> getUsersByTask(UUID taskId) {
        return dslContext.select().from(Tables.USERS)
                .join(Tables.TASK_ASSIGNEES).on(Tables.TASK_ASSIGNEES.USER_ID
                        .eq(Tables.USERS.ID))
                .where(Tables.TASK_ASSIGNEES.TASK_ID.eq(taskId))
                .fetchInto(Users.class);
    }

    @Override
    public List<Tasks> getTasksByUser(UUID userId) {
        return dslContext.select().from(Tables.TASK_ASSIGNEES)
                .join(Tables.TASK_ASSIGNEES).on(Tables.TASK_ASSIGNEES.TASK_ID
                        .eq(Tables.TASKS.ID))
                .where(Tables.USERS.ID.eq(userId))
                .fetchInto(Tasks.class);
    }
}
