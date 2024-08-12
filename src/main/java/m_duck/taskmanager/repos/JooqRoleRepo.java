package m_duck.taskmanager.repos;

import m_duck.taskmanager.exception.RoleException;
import m_duck.taskmanager.model.Tables;
import m_duck.taskmanager.model.tables.pojos.Roles;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JooqRoleRepo implements RoleRepo{

    private final DSLContext dslContext;

    @Autowired
    public JooqRoleRepo(DSLContext dslContext){
        this.dslContext=dslContext;
    }

    @Override
    public boolean saveRole(Roles role) {
       return dslContext.insertInto(Tables.ROLES).set(dslContext.newRecord(Tables.ROLES,role)).onDuplicateKeyIgnore().execute()==1;
    }

    private boolean checkRole(UUID id){
        return dslContext.selectFrom(Tables.ROLES).where(Tables.ROLES.ID.eq(id)).execute()>0;
    }

    @Override
    public List<Roles> getAllRoles() {
        return dslContext.selectFrom(Tables.ROLES).fetchInto(Roles.class);
    }

    @Override
    public Roles getRoleByName(String roleName) {
        return dslContext.selectFrom(Tables.ROLES)
                .where(Tables.ROLES.NAME.eq(roleName.toUpperCase()))
                .fetchOneInto(Roles.class);
    }

    @Override
    public Roles getRoleById(UUID id) {
        return dslContext.selectFrom(Tables.ROLES)
                .where(Tables.ROLES.ID.eq(id))
                .fetchOneInto(Roles.class);
    }

    @Override
    public boolean updateRole(UUID id, Roles role) {
        if(!checkRole(id)){
           return false;
        }
        if(getRoleByName(role.getName())!=null){
            return false;
        }
        return dslContext.update(Tables.ROLES)
                .set(Tables.ROLES.NAME,role.getName().toUpperCase())
                .where(Tables.ROLES.ID.eq(id))
                .execute()==1;
    }

    @Override
    public boolean deleteRole(UUID id) {
        return dslContext.delete(Tables.ROLES).where(Tables.ROLES.ID.eq(id)).execute()==1;
    }
}
