package m_duck.taskmanager.service;

import m_duck.taskmanager.model.tables.pojos.Roles;
import m_duck.taskmanager.model.tables.pojos.Users;
import m_duck.taskmanager.repos.JooqUserRepo;
import m_duck.taskmanager.repos.JooqUserRolesRepo;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private JooqUserRepo userRepo;

    @Autowired
    private JooqUserRolesRepo userRolesRepo;

    @Override
    public UserDetails loadUserByUsername(String value) throws UsernameNotFoundException {
        Users user = userRepo.getUserByUserNameOrEmail(value);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<Roles> roles = userRolesRepo.allRolesOfUser(user.getUsername());

        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        System.out.println(
                "MyUserDetailsService " + value
        );
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
