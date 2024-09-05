package m_duck.taskmanager.repos;

import m_duck.taskmanager.DTO.TaskView;
import m_duck.taskmanager.model.Tables;
import m_duck.taskmanager.model.tables.pojos.Tasks;
import m_duck.taskmanager.model.tables.pojos.Users;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class JooqTaskAssRepo
        implements TaskAssigneesRepo {

    private final DSLContext dslContext;

    @Autowired
    public JooqTaskAssRepo(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    private boolean updateTask(UUID taskId) {
        LocalDateTime now = LocalDateTime.now();
        return dslContext.update(Tables.TASKS).set(Tables.TASKS.UPDATED_AT, now).where(Tables.TASKS.ID.eq(taskId)).execute() == 1;
    }

    @Override
    public boolean addOrUpdateTaskByUser(UUID taskId, String username) {
        UUID user_id = dslContext.select(Tables.USERS.ID).from(Tables.USERS).where(Tables.USERS.USERNAME.eq(username)).fetchOne(Tables.USERS.ID);
        LocalDateTime now = LocalDateTime.now();
        int result = dslContext.insertInto(Tables.TASK_ASSIGNEES)
                .set(Tables.TASK_ASSIGNEES.TASK_ID, taskId)
                .set(Tables.TASK_ASSIGNEES.USER_ID, user_id)
                .set(Tables.TASK_ASSIGNEES.CREATED_AT, now)
                .onConflict(Tables.TASK_ASSIGNEES.TASK_ID, Tables.TASK_ASSIGNEES.USER_ID)
                .doUpdate()
                .set(Tables.TASK_ASSIGNEES.TASK_ID, taskId)
                .execute();
        boolean updatedTask = updateTask(taskId);
        if (result == 1 && updatedTask) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteTaskByUser(UUID taskId, UUID userId) {
        int result = dslContext.delete(Tables.TASK_ASSIGNEES)
                .where(Tables.TASK_ASSIGNEES.TASK_ID.eq(taskId))
                .and(Tables.TASK_ASSIGNEES.USER_ID.eq(userId))
                .execute();
        boolean updatedTask = updateTask(taskId);
        if (result == 1 && updatedTask) {
            return true;
        }
        return false;
    }

    @Override
    public List<TaskView> getUsersByTask(UUID taskId) {
        return dslContext.select(Tables.USERS.USERNAME.as("username"), Tables.ROLES.NAME.as("role"), Tables.TASK_ASSIGNEES.CREATED_AT.as("createdAt"))
                .from(Tables.USERS)
                .join(Tables.TASK_ASSIGNEES).on(Tables.TASK_ASSIGNEES.USER_ID.eq(Tables.USERS.ID))
                .join(Tables.USER_ROLES).on(Tables.USER_ROLES.USER_ID.eq(Tables.USERS.ID))
                .join(Tables.ROLES).on(Tables.ROLES.ID.eq(Tables.USER_ROLES.ROLE_ID))
                .where(Tables.TASK_ASSIGNEES.TASK_ID.eq(taskId))
                .fetchInto(TaskView.class);
    }

    @Override
    public List<Tasks> getTasksByUser(UUID userId) {
        return dslContext.select()
                .from(Tables.TASKS)
                .join(Tables.TASK_ASSIGNEES).on(Tables.TASK_ASSIGNEES.TASK_ID
                        .eq(Tables.TASKS.ID))
                .where(Tables.TASK_ASSIGNEES.USER_ID.eq(userId))
                .fetchInto(Tasks.class);
    }

    @Override
    public boolean deleteUserByTask(UUID task_id, String username) {
        UUID user = dslContext.select(Tables.USERS.ID).from(Tables.USERS)
                .where(Tables.USERS.USERNAME.eq(username)).fetchOne(Tables.USERS.ID);
        int result = dslContext.delete(Tables.TASK_ASSIGNEES)
                .where(Tables.TASK_ASSIGNEES.TASK_ID.eq(task_id))
                .and(Tables.TASK_ASSIGNEES.USER_ID.eq(user)).execute();
        boolean updatedTask = updateTask(task_id);
        if (result == 1 && updatedTask) {
            return true;
        }
        return false;
    }

    public List<TaskView> getUsersWithoutTask() {
        return dslContext.select(Tables.USERS.USERNAME.as("username"), Tables.ROLES.NAME.as("role"), Tables.TASK_ASSIGNEES.CREATED_AT.as("createdAt"))
                .from(Tables.USERS)
                .leftJoin(Tables.TASK_ASSIGNEES).on(Tables.TASK_ASSIGNEES.USER_ID.eq(Tables.USERS.ID))
                .join(Tables.USER_ROLES).on(Tables.USER_ROLES.USER_ID.eq(Tables.USERS.ID))
                .join(Tables.ROLES).on(Tables.ROLES.ID.eq(Tables.USER_ROLES.ROLE_ID))
                .where(Tables.TASK_ASSIGNEES.TASK_ID.isNull())
                .fetchInto(TaskView.class);
    }

    public List<TaskView> getUsersWithOtherTaskOrWithoutTask(UUID task_id) {
        return dslContext.select(Tables.USERS.USERNAME.as("username"), Tables.ROLES.NAME.as("role"), Tables.TASK_ASSIGNEES.CREATED_AT.as("createdAt"))
                .from(Tables.USERS)
                .leftJoin(Tables.TASK_ASSIGNEES).on(Tables.TASK_ASSIGNEES.USER_ID.eq(Tables.USERS.ID))
                .join(Tables.USER_ROLES).on(Tables.USER_ROLES.USER_ID.eq(Tables.USERS.ID))
                .join(Tables.ROLES).on(Tables.ROLES.ID.eq(Tables.USER_ROLES.ROLE_ID))
                .where(Tables.TASK_ASSIGNEES.TASK_ID.isNull())
                .or(Tables.TASK_ASSIGNEES.TASK_ID.notEqual(task_id))
                .fetchInto(TaskView.class);
    }
}
