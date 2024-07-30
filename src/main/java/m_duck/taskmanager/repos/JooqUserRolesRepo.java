package m_duck.taskmanager.repos;

import m_duck.taskmanager.model.Tables;
import m_duck.taskmanager.model.tables.pojos.Roles;
import m_duck.taskmanager.model.tables.pojos.UserRoles;
import m_duck.taskmanager.model.tables.pojos.Users;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Repository
public class JooqUserRolesRepo implements UserRolesRepo{

    private final DSLContext dslContext;

    @Autowired
    public JooqUserRolesRepo(DSLContext dslContext){
        this.dslContext=dslContext;
    }

    @Override
    public boolean addOrUpdateRoleForUser(UUID user_id, UUID role_id) {
        return dslContext.insertInto(Tables.USER_ROLES)
                .set(Tables.USER_ROLES.ROLE_ID,role_id)
                .set(Tables.USER_ROLES.USER_ID,user_id)
                .onConflict(Tables.USER_ROLES.USER_ID,Tables.USER_ROLES.ROLE_ID)
                .doUpdate()
                .set(Tables.USER_ROLES.ROLE_ID,role_id)
                .execute()==1;
    }

    @Override
    public boolean deleteRoleForUser(UUID user_id, UUID role_id) {
        return dslContext.delete(Tables.USER_ROLES)
                .where(Tables.USER_ROLES.ROLE_ID.eq(role_id))
                .and(Tables.USER_ROLES.USER_ID.eq(user_id))
                .execute()==1;
    }

    @Override
    public List<Roles> allRolesOfUser(String username) {
        return dslContext.select(Tables.ROLES)
                .from(Tables.ROLES)
                .join(Tables.USER_ROLES).on(Tables.USER_ROLES.ROLE_ID.eq(Tables.ROLES.ID))
                .join(Tables.USERS).on(Tables.USERS.ID.eq(Tables.USER_ROLES.USER_ID))
                .where(Tables.USERS.USERNAME.eq(username)).fetchInto(Roles.class);
    }

    @Override
    public List<Users> allUsersOfRoles(String roleName) {
        return dslContext.select(Tables.USERS)
                .from(Tables.USERS)
                .join(Tables.USER_ROLES).on(Tables.USERS.ID.eq(Tables.USER_ROLES.USER_ID))
                .join(Tables.ROLES).on(Tables.ROLES.ID.eq(Tables.USER_ROLES.ROLE_ID))
                .where(Tables.ROLES.NAME.eq(roleName))
                .fetchInto(Users.class);
    }
}
