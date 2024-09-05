package m_duck.taskmanager;

import m_duck.taskmanager.model.tables.pojos.Tasks;
import m_duck.taskmanager.model.tables.pojos.Users;
import m_duck.taskmanager.repos.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class TaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

    private void createUser(int count) {

    }

    @Bean
    public CommandLineRunner demo(JooqTaskAssRepo taskAssRepo,
                                  JooqTaskRepo taskRepo,
                                  JooqUserRepo userRepo,
                                  JooqRoleRepo roleRepo,
                                  JooqUserRolesRepo userRolesRepo) {
        return (args -> {
/*
            List<Users> users = userRepo.getAllUsers();
            List<Tasks> tasks = taskRepo.getAllTasks();

            for(int i = 0; i<tasks.size(); i++){
                for(int j = 3*i; j<3*i+3;j++){
                    taskAssRepo.addOrUpdateTaskByUser(
                            tasks.get(i).getId(),
                            users.get(j).getId());
                }
            }




            Faker faker = new Faker();
            Users user = new Users();
            user.setUsername(faker.name().username());
            user.setPassword(faker.internet().password());
            user.setEmail(faker.internet().emailAddress());
            userRepo.saveUser(user);

            Faker faker = new Faker();
            for (int i = 0; i < 50; i++) {
                Users users = new Users();
                users.setUsername(faker.name().username());
                users.setEmail(faker.internet().emailAddress());
                users.setPassword(faker.internet().password());
                userRepo.saveUser(users);
            }

            List<Roles> roleId = roleRepo.getAllRoles();
            List<Users> userId = userRepo.getAllUsers();
            System.out.println("work");
            for(int i=1;i<roleId.size();i++){
                for (int j = 5*(i-1);j<5*(i-1)+5;j++){
                    //System.out.println(i+" "+j);
                    //System.out.println((userId.get(j).getId()+" "+roleId.get(i).getId()));
                    //userRolesRepo.addOrUpdateRoleForUser(userId.get(j).getId(),roleId.get(i).getId());
                }
            }
             */
        });
    }


}
