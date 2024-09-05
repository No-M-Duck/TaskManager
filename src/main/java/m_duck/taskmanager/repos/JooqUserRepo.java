package m_duck.taskmanager.repos;

import m_duck.taskmanager.model.Tables;
import m_duck.taskmanager.model.tables.pojos.Users;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
        LocalDateTime localDateTime = LocalDateTime.now();
        user.setCreatedAt(localDateTime);
        user.setUpdatedAt(localDateTime);
        return dslContext.insertInto(Tables.USERS).set(dslContext.newRecord(Tables.USERS,user)).onDuplicateKeyIgnore().execute()==1;
    }

    @Override
    public List<Users> getAllUsers() {
        return dslContext.selectFrom(Tables.USERS).fetchInto(Users.class);
    }

    @Override
    public Users getUserByUserNameOrEmail(String value) {
        return dslContext.selectFrom(Tables.USERS)
                .where(Tables.USERS.USERNAME.eq(value))
                .or(Tables.USERS.EMAIL.eq(value))
                .fetchOneInto(Users.class);
    }

    @Override
    public Users getUserById(UUID id) {
        return dslContext.selectFrom(Tables.USERS)
                .where(Tables.USERS.ID.eq(id)).fetchOneInto(Users.class);
    }

    private boolean checkUser(UUID uuid){
        return dslContext.selectFrom(Tables.USERS)
                .where(Tables.USERS.ID.eq(uuid)).execute()>0;
    }
    public boolean checkUser(String username){
        return dslContext.selectFrom(Tables.USERS)
                .where(Tables.USERS.USERNAME.eq(username)).execute()<1;
    }
    public boolean checkEmail(String email){
        return dslContext.selectFrom(Tables.USERS)
                .where(Tables.USERS.EMAIL.eq(email)).execute()<1;
    }

    @Override
    public boolean updateUser(UUID id,Users user) {
        if(!checkUser(id)){
            return false;
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        user.setUpdatedAt(localDateTime);
        return dslContext.update(Tables.USERS)
                .set(dslContext.newRecord(Tables.USERS,user))
                .where(Tables.USERS.ID.eq(id)).execute()==1;

    }

    @Override
    public boolean deleteUser(UUID id) {
        return dslContext.delete(Tables.USERS).where(Tables.USERS.ID.eq(id)).execute()==1;
    }
}
