package m_duck.taskmanager.repos;

import m_duck.taskmanager.model.Tables;
import m_duck.taskmanager.model.tables.pojos.Roles;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JooqRoleRepo implements RoleRepo{

    private final DSLContext dslContext;

    @Autowired
    public JooqRoleRepo(DSLContext dslContext){
        this.dslContext=dslContext;
    }

    @Override
    public boolean saveRole(Roles role) {
       return dslContext.insertInto(Tables.ROLES).set(dslContext.newRecord(Tables.ROLES,role)).execute()==1;
    }

    @Override
    public List<Roles> getAllRoles() {
        return dslContext.selectFrom(Tables.ROLES).fetchInto(Roles.class);
    }

    @Override
    public Roles getRoleByName(String roleName) {
        return dslContext.selectFrom(Tables.ROLES)
                .where(Tables.ROLES.NAME.eq(roleName))
                .fetchOneInto(Roles.class);
    }

    @Override
    public boolean updateRole(Roles role) {
         return dslContext.update(Tables.ROLES)
                .set(Tables.ROLES.NAME,role.getName())
                .where(Tables.ROLES.ID.eq(role.getId()))
                .execute()==1;
    }

    @Override
    public boolean deleteRole(Roles role) {
        return dslContext.delete(Tables.ROLES).where(Tables.ROLES.NAME.eq(role.getName())).execute()==1;
    }
}
