package m_duck.taskmanager.repos;

import m_duck.taskmanager.model.Tables;
import m_duck.taskmanager.model.tables.pojos.Users;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JooqUserRepo implements UserRepo{

    private final DSLContext dslContext;

    @Autowired
    public JooqUserRepo(DSLContext dslContext){
        this.dslContext = dslContext;
    }

    @Override
    public boolean saveUser(Users user) {
        return dslContext.insertInto(Tables.USERS).set(dslContext.newRecord(Tables.USERS,user)).onDuplicateKeyIgnore().execute()==1;
    }

    @Override
    public List<Users> getAllUsers() {
        return dslContext.selectFrom(Tables.USERS).fetchInto(Users.class);
    }

    @Override
    public Users getUserByUserNameOrEmail(Users user) {
        return dslContext.selectFrom(Tables.USERS)
                .where(Tables.USERS.USERNAME.eq(user.getUsername()))
                .or(Tables.USERS.EMAIL.eq(user.getEmail()))
                .fetchOneInto(Users.class);
    }

    private boolean checkUser(UUID uuid){
        return dslContext.selectFrom(Tables.USERS)
                .where(Tables.USERS.ID.eq(uuid)).execute()>0;
    }

    @Override
    public boolean updateUser(UUID id,Users user) {
        if(!checkUser(id)){
            return false;
        }
        return dslContext.update(Tables.USERS)
                .set(dslContext.newRecord(Tables.USERS,user))
                .where(Tables.USERS.ID.eq(id)).execute()==1;

    }

    @Override
    public boolean deleteUser(UUID id) {
        return dslContext.delete(Tables.USERS).where(Tables.USERS.ID.eq(id)).execute()==1;
    }
}
